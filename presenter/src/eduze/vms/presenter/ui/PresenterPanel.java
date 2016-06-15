package eduze.vms.presenter.ui;

import eduze.vms.presenter.logic.*;
import eduze.vms.presenter.logic.mpi.facilitator.Facilitator;
import eduze.vms.presenter.logic.mpi.facilitator.FacilitatorImplServiceLocator;
import eduze.vms.presenter.logic.mpi.facilitator.InvalidFacilitatorPasskeyException;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.xml.rpc.ServiceException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Created by Madhawa on 13/04/2016.
 */
public class PresenterPanel {

    private JPanel mainPanel;
    private JTextField txtFacilitatorURL;
    private JPasswordField txtFacilitatorPasskey;
    private JTextField txtPresenterName;
    private JButton requestConnectionButton;
    private JButton requestScreenShareButton;
    private JCheckBox screenActiveCheckBox;
    private JCheckBox speechActiveCheckbox;
    private JButton requestSpeechShareButton;
    private JButton btnAllShare;
    private JCheckBox allowedScreenShareCheckBox;
    private JCheckBox allowedSpeechShareCheckbox;
    private JButton disconnectButton;
    private JFrame mainFrame;

    private PresenterController controller = null;
    private FacilitatorConnector connector = null;
    public PresenterPanel()  {
        mainFrame = new JFrame("VMS Presenter Panel");
        mainFrame.setContentPane(this.mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // mainFrame.pack();
        mainFrame.setSize(800,600);

        requestConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRequestConnectionClicked();
            }
        });
        requestScreenShareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onScreenShareRequest();
            }
        });
        requestSpeechShareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSpeechShareRequest();
            }
        });

        btnAllShare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAllShareRequest();
            }
        });
        allowedScreenShareCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setAllowedScreenShare(allowedScreenShareCheckBox.isSelected());
            }
        });
        allowedSpeechShareCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setAllowedAudioShare(allowedSpeechShareCheckbox.isSelected());
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.disconnect();
                } catch (FacilitatorConnectionException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void onAllShareRequest() {
        try {
            controller.requestScreenShare(true);
        } catch (FacilitatorConnectionException e) {
            e.printStackTrace();
        }
    }

    private void onSpeechShareRequest() {
        try {
            controller.requestAudioShare();
        } catch (FacilitatorConnectionException e) {
            e.printStackTrace();
        }
    }

    private void onScreenShareRequest() {
        try {
            controller.requestScreenShare(false);
        } catch (FacilitatorConnectionException e) {
            e.printStackTrace();
        }
    }


    private void onRequestConnectionClicked() {
        FacilitatorConnector.Configuration configuration = new FacilitatorConnector.Configuration();
        configuration.setFacilitatorURL(txtFacilitatorURL.getText());
        configuration.setFacilitatorPasskey(txtFacilitatorPasskey.getPassword());
        configuration.setPresenterName(txtPresenterName.getText());

        try {
            connector = FacilitatorConnector.connect(configuration);
            connector.setConnectionRequestStateListener(new FacilitatorConnector.ConnectionRequestStateListener() {
                @Override
                public void onSuccess(FacilitatorConnector sender) {
                    try {
                        controller = sender.obtainController();

                        controller.addStateChangeListener(new ControlLoop.StateChangeListener() {
                            @Override
                            public void onScreenCaptureChanged(boolean newValue) {
                                screenActiveCheckBox.setSelected(newValue);
                            }

                            @Override
                            public void onAudioCaptureChanged(boolean newValue) {
                                speechActiveCheckbox.setSelected(newValue);
                            }

                            @Override
                            public void onControlLoopCycleCompleted() {

                            }

                            @Override
                            public void onFacilitatorDisconnected() {
                                JOptionPane.showMessageDialog(mainFrame,"Facilitator has been disconnected","Disconnected",JOptionPane.OK_OPTION);
                            }
                        });

                        controller.getAssignedTasksManager().addAssignedTaskListener(new AssignedTasksManager.AssignedTaskListener() {
                            @Override
                            public void onInitiated() {
                                JOptionPane.showMessageDialog(mainFrame,"Assigned Tasks initiated");
                            }

                            @Override
                            public void onTaskAssigned(AssignedTaskInfo task) {
                                JOptionPane.showMessageDialog(mainFrame,"New Task added " + task.getTitle());

                            }

                            @Override
                            public void onTaskUnAssigned(AssignedTaskInfo task) {
                                JOptionPane.showMessageDialog(mainFrame,"Task Removed " + task.getTitle());
                            }

                            @Override
                            public void onTaskModified(AssignedTaskInfo oldTask, AssignedTaskInfo newTask) {
                                JOptionPane.showMessageDialog(mainFrame, "Task Modified; Title : " + oldTask.getTitle() + " >> " + newTask.getTitle() + "\nDescription: "+ oldTask.getDescription() + " >> " + newTask.getDescription());
                            }
                        });

                        JOptionPane.showMessageDialog(mainFrame,"Connection Successful");
                    } catch (FacilitatorConnectionNotReadyException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame,"Facilitator Connection Not Ready");
                    } catch (FacilitatorConnectionException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame,"Facilitator Connection Error");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FacilitatorDisconnectedException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame,"Facilitator Connection Error");
                    }

                }

                @Override
                public void onFailed(FacilitatorConnector sender) {
                    JOptionPane.showMessageDialog(mainFrame,"Connection Failed");
                }

                @Override
                public boolean onException(FacilitatorConnector sender, Exception e) {
                    JOptionPane.showMessageDialog(mainFrame,"Error on listener");
                    return true;
                }
            });
        } catch (FacilitatorConnectionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,"Facilitator Connection Error");
        } catch (InvalidFacilitatorPasskeyException e) {
            JOptionPane.showMessageDialog(mainFrame,"Invalid Passkey");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(mainFrame,"Error in Facilitator URL");
            e.printStackTrace();
        }
    }

    public void run()
    {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        PresenterPanel panel = new PresenterPanel();
        panel.run();
    }

    private void oldTestCode()
    {
        FacilitatorImplServiceLocator locator = new FacilitatorImplServiceLocator();
        try {
            Facilitator facilitator =  locator.getFacilitatorImplPort(new URL("http://localhost:7000/facilitator"));
            Logger.getLogger(getClass()).info("Facilitator Name " + facilitator.getFacilitatorName());

            String result = facilitator.requestConnection("My Presenter", "password");
            Logger.getLogger(getClass()).info("Connection Request Id " + result);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
