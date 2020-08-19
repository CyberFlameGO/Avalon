package me.themgrf.avalon.utils.opengl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class VAO {

    public static final int BYTES_IN_FLOAT = 4, BYTES_IN_INT = 4;

    private List<VBO> relatedVbos = new ArrayList<VBO>();
    private VBO indexBuffer;
    private List<Attribute> attributes = new ArrayList<Attribute>();

    public final int id;

    public static VAO create() {
        int id = GL30.glGenVertexArrays();
        return new VAO(id);
    }

    private VAO(int id) {
        this.id = id;
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void enableAttributes() {
        for (Attribute attribute : attributes) {
            attribute.enable(true);
        }
    }

    public void disableAttribs(int... attribs) {
        for (int i : attribs) {
            GL20.glDisableVertexAttribArray(i);
        }
    }

    public VBO createDataFeed(int maxVertexCount, int usage, Attribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        VBO vbo = VBO.create(GL15.GL_ARRAY_BUFFER, usage);
        relatedVbos.add(vbo);
        vbo.allocateData(bytesPerVertex * maxVertexCount);
        linkAttributes(bytesPerVertex, newAttributes);
        vbo.unbind();
        return vbo;
    }

    public VBO initDataFeed(FloatBuffer data, int usage, Attribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        VBO vbo = VBO.create(GL15.GL_ARRAY_BUFFER, usage);
        relatedVbos.add(vbo);
        vbo.allocateData(data.limit() * BYTES_IN_FLOAT);
        vbo.storeData(0, data);
        linkAttributes(bytesPerVertex, newAttributes);
        vbo.unbind();
        return vbo;
    }

    public VBO initDataFeed(ByteBuffer data, int usage, Attribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        VBO vbo = VBO.create(GL15.GL_ARRAY_BUFFER, usage);
        relatedVbos.add(vbo);
        vbo.allocateData(data.limit());
        vbo.storeData(0, data);
        linkAttributes(bytesPerVertex, newAttributes);
        vbo.unbind();
        return vbo;
    }

    public void linkBoundVbo(VBO vbo, Attribute... newAttributes) {
        int bytesPerVertex = getVertexDataTotalBytes(newAttributes);
        linkAttributes(bytesPerVertex, newAttributes);
        relatedVbos.add(vbo);
    }

    public VBO createIndexBuffer(IntBuffer indices) {
        this.indexBuffer = VBO.create(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
        indexBuffer.allocateData(indices.limit() * BYTES_IN_INT);
        indexBuffer.storeData(0, indices);
        return indexBuffer;
    }

    public void delete(boolean deleteVbos) {
        GL30.glDeleteVertexArrays(id);
        if (deleteVbos) {
            for (VBO vbo : relatedVbos) {
                vbo.delete();
            }
        }
    }

    private void linkAttributes(int bytesPerVertex, Attribute... newAttributes) {
        int offset = 0;
        for (Attribute attribute : newAttributes) {
            attribute.link(offset, bytesPerVertex);
            offset += attribute.bytesPerVertex;
            attribute.enable(true);
            attributes.add(attribute);
        }
    }

    private int getVertexDataTotalBytes(Attribute... newAttributes) {
        int total = 0;
        for (Attribute attribute : newAttributes) {
            total += attribute.bytesPerVertex;
        }
        return total;
    }


}
