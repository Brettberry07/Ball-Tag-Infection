//package com.libgdx.example.controllers;
//
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.utils.IntIntMap;
//
//public class InputControllers extends InputAdapter {
//        public int strafeLeftKey = Input.Keys.A;
//        public int strafeRightKey = Input.Keys.D;
//        public int jumpKey = Input.Keys.SPACE;
//        public int runShiftKey = Input.Keys.SHIFT_LEFT;
//
//        private final IntIntMap keys = new IntIntMap();
//
//        @Override
//        public boolean keyDown (int keycode) {
//            keys.put(keycode, keycode);
//            return true;
//        }
//
//        @Override
//        public boolean keyUp (int keycode) {
//            keys.remove(keycode, 0);
//            return true;
//        }
//
//        public void update (GameObject player, float deltaTime) {
//            float moveSpeed = Settings.walkSpeed;
//            if (keys.containsKey(runShiftKey) || keys.containsKey(runShiftAlt)) {
//                moveSpeed *= Settings.runFactor;
//            }
//
//            // note: most of the following is only valid when on ground, but we leave it to allow some fun cheating
//            if (keys.containsKey(forwardKey) || keys.containsKey(forwardAlt))
//                moveForward(deltaTime * moveSpeed);
//            if (keys.containsKey(backwardKey) || keys.containsKey(backwardAlt))
//                moveForward(-deltaTime * moveSpeed);
//            if (keys.containsKey(strafeLeftKey))
//                strafe(-deltaTime * Settings.walkSpeed);
//            if (keys.containsKey(strafeRightKey))
//                strafe(deltaTime * Settings.walkSpeed);
//            if (keys.containsKey(turnLeftKey))
//                rotateView(deltaTime * Settings.turnSpeed, 0);
//            if (keys.containsKey(turnRightKey))
//                rotateView(-deltaTime * Settings.turnSpeed, 0);
//
//            if ((isOnGround && keys.containsKey(jumpKey))) {
//                linearForce.y = Settings.jumpForce;
//            }
//        }
//}
