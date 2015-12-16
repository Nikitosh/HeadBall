package com.nikitosh.headball;

public class Move {
    private boolean isHit = false;
    private boolean isJump = false;
    private boolean isLeft = false;
    private boolean isRight = false;

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

}
