package com.kirill.kochnev.homewardrope.presentation.ui.fragments;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.presentation.ui.fragments.gl.Square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GLCollageFragment extends Fragment {

    @BindView(R.id.glView)
    GLSurfaceView glView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gl_collage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        glView.setRenderer(new GlRenderer(new Thread()));
    }

    @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }

    private class GlRenderer implements GLSurfaceView.Renderer {

        @NonNull final Thread thread;

        private final Square square = new Square();

        GlRenderer(@NonNull final Thread thread) {
            this.thread = thread;
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set the background color to black ( rgba ).
            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
            // Enable Smooth Shading, default not really needed.
//            gl.glShadeModel(GL10.GL_SMOOTH);
//            // Depth buffer setup.
//            gl.glClearDepthf(1.0f);
//            // Enables depth testing.
//            gl.glEnable(GL10.GL_DEPTH_TEST);
//            // The type of depth testing to do.
//            gl.glDepthFunc(GL10.GL_LEQUAL);
//            // Really nice perspective calculations.
//            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        }

        public void onDrawFrame(GL10 gl) {
            // Clears the screen and depth buffer.
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            // Replace the current matrix with the identity matrix
            gl.glLoadIdentity();
            // Translates 4 units into the screen.
            gl.glTranslatef(0, 0, -4);
            // Draw our square.
            square.draw(gl);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // Sets the current view port to the new size.
            gl.glViewport(0, 0, width, height);
            // Select the projection matrix
            gl.glMatrixMode(GL10.GL_PROJECTION);
//            // Reset the projection matrix
            gl.glLoadIdentity();
//            // Calculate the aspect ratio of the window
            GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                    100.0f);
//            // Select the modelview matrix
            gl.glMatrixMode(GL10.GL_MODELVIEW);
//            // Reset the modelview matrix
            gl.glLoadIdentity();
        }
    }

}
