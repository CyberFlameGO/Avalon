package me.themgrf.avalon.utils.data;

import me.themgrf.avalon.utils.opengl.Attribute;
import me.themgrf.avalon.utils.opengl.VAO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class VAOLoader {

    public static VAO createVao(byte[] meshData, int[] indices) {
        VAO vao = VAO.create();
        vao.bind();
        storeVertexDataInVao(vao, meshData);
        if (indices != null) {
            storeIndicesInVao(vao, indices);
        }
        vao.unbind();
        return vao;
    }

    public static VAO createVaoNoNormals(byte[] meshData, int[] indices) {
        VAO vao = VAO.create();
        vao.bind();
        storeVertexDataInVaoNoNormals(vao, meshData);
        if (indices != null) {
            storeIndicesInVao(vao, indices);
        }
        vao.unbind();
        return vao;
    }

    private static void storeVertexDataInVaoNoNormals(VAO vao, byte[] meshData) {
        ByteBuffer buffer = storeMeshDataInBuffer(meshData);
        vao.initDataFeed(buffer, GL15.GL_STATIC_DRAW, new Attribute(0, GL11.GL_FLOAT, 3),
                new Attribute(1, GL11.GL_UNSIGNED_BYTE, 4, true));
    }

    private static void storeVertexDataInVao(VAO vao, byte[] meshData) {
        ByteBuffer buffer = storeMeshDataInBuffer(meshData);
        vao.initDataFeed(buffer, GL15.GL_STATIC_DRAW, new Attribute(0, GL11.GL_FLOAT, 3),
                new Attribute(1, GL12.GL_UNSIGNED_INT_2_10_10_10_REV, 4, true),
                new Attribute(2, GL11.GL_UNSIGNED_BYTE, 4, true));
    }

    private static ByteBuffer storeMeshDataInBuffer(byte[] meshData){
        ByteBuffer buffer = BufferUtils.createByteBuffer(meshData.length);
        buffer.put(meshData);
        buffer.flip();
        return buffer;
    }

    private static void storeIndicesInVao(VAO vao, int[] indices) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(indices.length);
        intBuffer.put(indices);
        intBuffer.flip();
        vao.createIndexBuffer(intBuffer);
    }

}
