package me.themgrf.avalon.renderer.models;

public class RawModel {

    private int vaoID;
    private int vortexCount;

    public RawModel(int vaoID, int vortexCount) {
        this.vaoID = vaoID;
        this.vortexCount = vortexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

    public int getVortexCount() {
        return vortexCount;
    }

    public void setVortexCount(int vortexCount) {
        this.vortexCount = vortexCount;
    }
}
