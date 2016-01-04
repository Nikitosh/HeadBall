package com.nikitosh.headball;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Move {
    private boolean isHit = false;
    private boolean isJump = false;
    private boolean isLeft = false;
    private boolean isRight = false;

    public Move() {
    }

    public Move(boolean isHit, boolean isJump, boolean isLeft, boolean isRight) {
        this.isHit = isHit;
        this.isJump = isJump;
        this.isLeft = isLeft;
        this.isRight = isRight;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean isJump) {
        this.isJump = isJump;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean isRight) {
        this.isRight = isRight;
    }

    public void serialize(DataOutputStream outputStream) {
        try {
            outputStream.writeUTF((isHit ? "1" : "0") + (isJump ? "1" : "0") + (isLeft ? "1" : "0") + (isRight ? "1" : "0") + "\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Move deserialize(DataInputStream inputStream) {
        int b = 0;
        try {
            String moveString = inputStream.readUTF();
            Move move = new Move(false, false, false, false);
            if (moveString.charAt(0) == '1') {
                move.isHit = true;
            }
            if (moveString.charAt(1) == '1') {
                move.isJump = true;
            }
            if (moveString.charAt(2) == '1') {
                move.isLeft = true;
            }
            if (moveString.charAt(3) == '1') {
                move.isRight = true;
            }
            return move;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
