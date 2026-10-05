cat > app/src/main/java/com/turrinistudio/gtav/MainActivity.java << 'EOF'
package com.turrinistudio.gtav;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MainActivity extends Activity {
    float carX=0, carZ=0, carRot=0, speed=0;
    boolean gas=false, brake=false, l=false, r=false;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        GLSurfaceView glView = new GLSurfaceView(this);
        glView.setRenderer(new GLSurfaceView.Renderer() {
            FloatBuffer cubeBuf;
            public void onSurfaceCreated(GL10 gl, EGLConfig c) {
                gl.glClearColor(0.18f, 0.55f, 0.95f, 1f);
                gl.glEnable(GL10.GL_DEPTH_TEST);
                float[] v = {
                    -0.5f,-0.5f,0.5f, 0.5f,-0.5f,0.5f, -0.5f,0.5f,0.5f, 0.5f,0.5f,0.5f,
                    0.5f,-0.5f,-0.5f, -0.5f,-0.5f,-0.5f, 0.5f,0.5f,-0.5f, -0.5f,0.5f,-0.5f,
                    -0.5f,-0.5f,-0.5f, -0.5f,-0.5f,0.5f, -0.5f,0.5f,-0.5f, -0.5f,0.5f,0.5f,
                    0.5f,-0.5f,0.5f, 0.5f,-0.5f,-0.5f, 0.5f,0.5f,0.5f, 0.5f,0.5f,-0.5f,
                    -0.5f,0.5f,0.5f, 0.5f,0.5f,0.5f, -0.5f,0.5f,-0.5f, 0.5f,0.5f,-0.5f,
                    -0.5f,-0.5f,-0.5f, 0.5f,-0.5f,-0.5f, -0.5f,-0.5f,0.5f, 0.5f,-0.5f,0.5f
                };
                ByteBuffer bb = ByteBuffer.allocateDirect(v.length*4);
                bb.order(ByteOrder.nativeOrder());
                cubeBuf = bb.asFloatBuffer();
                cubeBuf.put(v); cubeBuf.position(0);
            }
            public void onSurfaceChanged(GL10 gl, int w, int h) {
                gl.glViewport(0,0,w,h);
                gl.glMatrixMode(GL10.GL_PROJECTION);
                gl.glLoadIdentity();
                float ratio=(float)w/h;
                gl.glFrustumf(-ratio, ratio, -1, 1, 1.5f, 100);
            }
            public void onDrawFrame(GL10 gl) {
                if(gas) speed+=0.015f; if(brake) speed-=0.02f;
                if(l) carRot+=2.5f; if(r) carRot-=2.5f;
                if(speed>0.4f) speed=0.4f; if(speed<-0.2f) speed=-0.2f;
                speed*=0.985f;
                carX += Math.sin(Math.toRadians(carRot))*speed;
                carZ += Math.cos(Math.toRadians(carRot))*speed;

                gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glTranslatef(0, -2f, -12);
                gl.glRotatef(25,1,0,0);
                gl.glRotatef(-carRot,0,1,0);
                gl.glTranslatef(-carX, 0, -carZ);

                // Road
                gl.glPushMatrix();
                gl.glTranslatef(0,-0.5f,0);
                drawBox(gl, 100,0.2f,100, 0.15f,0.15f,0.15f);
                gl.glPopMatrix();
                gl.glPushMatrix();
                gl.glTranslatef(0,-0.39f,0);
                drawBox(gl, 0.5f,0.02f,100, 1f,1f,0.2f);
                gl.glPopMatrix();

                // Buildings
                for(int i=-5;i<=5;i++){
                    for(int j=-5;j<=5;j++){
                        if(i==0 && j==0) continue;
                        float bx=i*8; float bz=j*8;
                        float h = 2 + (Math.abs(i*3+j*7)%5);
                        gl.glPushMatrix();
                        gl.glTranslatef(bx, h/2-0.5f, bz);
                        drawBox(gl, 2.5f, h, 2.5f, 0.85f-0.05f*i, 0.85f, 0.75f);
                        gl.glPopMatrix();
                    }
                }

                // CAR
                gl.glPushMatrix();
                gl.glTranslatef(carX, 0.2f, carZ);
                gl.glRotatef(carRot,0,1,0);
                drawBox(gl, 1.2f,0.5f,2.2f, 0.95f,0.05f,0.05f);
                gl.glPushMatrix(); gl.glTranslatef(0,0.5f,-0.2f); drawBox(gl, 1f,0.4f,1f, 0.6f,0.05f,0.05f); gl.glPopMatrix();
                gl.glPushMatrix(); gl.glTranslatef(-0.65f,-0.3f,0.7f); drawBox(gl,0.2f,0.3f,0.4f,0.1f,0.1f,0.1f); gl.glPopMatrix();
                gl.glPushMatrix(); gl.glTranslatef(0.65f,-0.3f,0.7f); drawBox(gl,0.2f,0.3f,0.4f,0.1f,0.1f,0.1f); gl.glPopMatrix();
                gl.glPushMatrix(); gl.glTranslatef(-0.65f,-0.3f,-0.7f); drawBox(gl,0.2f,0.3f,0.4f,0.1f,0.1f,0.1f); gl.glPopMatrix();
                gl.glPushMatrix(); gl.glTranslatef(0.65f,-0.3f,-0.7f); drawBox(gl,0.2f,0.3f,0.4f,0.1f,0.1f,0.1f); gl.glPopMatrix();
                gl.glPopMatrix();
            }
            void drawBox(GL10 gl, float sx, float sy, float sz, float r, float g, float b){
                gl.glColor4f(r,g,b,1);
                gl.glPushMatrix();
                gl.glScalef(sx, sy, sz);
                gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeBuf);
                for(int i=0;i<6;i++) gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
                gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
                gl.glPopMatrix();
            }
        });

        TextView hud = new TextView(this){
            public boolean onTouchEvent(MotionEvent e){
                float x=e.getX(), y=e.getY(), w=getWidth(), h=getHeight();
                boolean down=e.getAction()!=MotionEvent.ACTION_UP;
                gas = down && x < w*0.3f && y < h*0.6f;
                brake = down && x < w*0.3f && y >= h*0.6f;
                l = down && x > w*0.7f && x < w*0.85f;
                r = down && x >= w*0.85f;
                return true;
            }
        };
        hud.setText("GTA MOROCCO v0.7 FIXED\nTANGIER CITY\n[GAS top-left] [BRAKE bottom-left]\n[◀ ▶ right side]");
        hud.setTextColor(0xFFFFFFFF);
        hud.setTextSize(12);
        hud.setPadding(20,20,20,20);

        FrameLayout layout=new FrameLayout(this);
        layout.addView(glView);
        layout.addView(hud);
        setContentView(layout);
    }
}
EOF
