package su.levenetc.android.textsurface.sample;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

import su.levenetc.android.textsurface.Debug;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.sample.checks.CookieThumperSample;

/**
 * Created by Eugene Levenetc.
 */
public class SampleActivity extends AppCompatActivity {

	private TextSurface textSurface;
    private CookieThumperSample sample;
    private ServerSocket serverSocket;
    private String[] time_history = {" ", " ", " ", " ", " ", " "};
    private int his_len = 6;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_activity);
		textSurface = (TextSurface) findViewById(R.id.text_surface);

		textSurface.postDelayed(new Runnable() {
            @Override
            public void run() {
                //show();
            }
        }, 1000);
        // Debug.ENABLED = true;
        textSurface.reset();
        sample = new CookieThumperSample(getAssets(), textSurface);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket udpSocket = new DatagramSocket(5656);
                    byte[] buffer = new byte[4096];
                    DatagramPacket packet = new DatagramPacket(buffer, 4096);

                    while (true) {
                        udpSocket.receive(packet);
                        String message = new String(packet.getData(), packet.getOffset(), packet.getLength());
                        if (message != null) {
                            Log.e("fuck", "after read");
                            String[] tmp1 = message.split(":");
                            if (tmp1.length < 3)
                                return;
                            String[] tmp2 = tmp1[0].split(" ");
                            String[] tmp3 = tmp1[2].split(" ");
                            final String time = tmp2[tmp2.length - 1] + "时" + tmp1[1] + "分" + tmp3[0] + "秒";
                            Log.e("fuck", time);
                            SampleActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sample.refreshUI(textSurface, time + "有人在屋内运动");
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.e("fuck", "prove alive");
                    e.printStackTrace();
                }
            }
        }).start();*/
	}

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(5656);
                    while (true) {
                        // Log.e("fuck", "before accept");
                        final Socket socket = serverSocket.accept();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    InputStream info = socket.getInputStream();
                                    DataInputStream dis = new DataInputStream(info);

                                    // Log.e("fuck", "before read");
                                    String tmp = dis.readLine();
                                    if (tmp != null) {
                                        // Log.e("fuck", "after read");
                                        String[] tmp1 = tmp.split(":");
                                        if (tmp1.length < 3)
                                            return;
                                        String[] tmp2 = tmp1[0].split(" ");
                                        String[] tmp3 = tmp1[2].split(" ");
                                        String time = tmp2[tmp2.length - 1] + "时" + tmp1[1] + "分" + tmp3[0] + "秒";
                                        // Log.e("fuck", time);
                                        for (int i = 0; i < his_len - 1; i++) {
                                            time_history[i] = time_history[i + 1];
                                        }
                                        time_history[his_len - 1] = time + "有人在屋内运动";
                                        SampleActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                sample.refresh(time_history);
                                            }
                                        });
                                    }
                                    dis.close();
                                    socket.close();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}