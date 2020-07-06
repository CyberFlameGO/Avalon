package me.themgrf.avalon.renderer.models;

import me.themgrf.avalon.Avalon;
import me.themgrf.avalon.renderer.ResourceLocation;
import me.themgrf.avalon.renderer.TexturePack;
import me.themgrf.avalon.utils.Logger;
import org.apache.commons.io.FileUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    public static RawModel loadModel(String fileName, Loader loader) {
        FileReader fileReader = null;
        TexturePack pack = Avalon.getInstance().getTexturePack();
        try {
            File file = File.createTempFile("model", "obj");
            FileUtils.copyInputStreamToFile(pack.getResourceStream(new ResourceLocation("models/" + fileName + ".obj")), file);
            fileReader = new FileReader(file);
        } catch (IOException e) {
            Logger.error("Failed to load file!");
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;

        try {
            while (true) {
                line = bufferedReader.readLine();
                final String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) { // Vertex position
                    vertices.add(new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]))
                    );
                } else if (line.startsWith("vt ")) { // Texture coords
                    textures.add(new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2])));
                } else if (line.startsWith("vn ")) { // Normal
                    normals.add(new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]))
                    );
                } else if (line.startsWith("f ")) { // Face
                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }

            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = bufferedReader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.getX();
            verticesArray[vertexPointer++] = vertex.getY();
            verticesArray[vertexPointer++] = vertex.getZ();
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, textureArray, indicesArray);
    }

    public static RawModel cube(Loader loader) {
        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        return loader.loadToVAO(vertices, textureCoords, indices);
    }

    private static void processVertex(
            String[] vertexData,
            List<Integer> indices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            float[] textureArray,
            float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTexture.getX();
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTexture.getY();
        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.getX();
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.getY();
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.getZ();
    }

}
