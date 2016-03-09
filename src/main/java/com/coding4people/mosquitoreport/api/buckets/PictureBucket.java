package com.coding4people.mosquitoreport.api.buckets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

import com.coding4people.mosquitoreport.api.Env;

@Singleton
public class PictureBucket extends Bucket {
    @Inject
    Env env;

    @Override
    protected String getBucketName() {
        return env.get("BUCKET_NAME_PICTURE").orElseThrow(() -> new IllegalArgumentException("Missing env var: BUCKET_NAME_PICTURE"));
    }

    // TODO make it parallel
    public Object put(String path, InputStream fileInputStream, Long contentLenght) {
        try {
            ByteArrayOutputStream buffer = bufferInputStream(fileInputStream);

            // TODO make it parallel
            uploadThumbnail(path, buffer, 480, 360);
            
            // TODO make it parallel
            return super.put(path, new ByteArrayInputStream(buffer.toByteArray()), Long.valueOf(buffer.size()));
        } catch (IOException e) {
            // TODO handle and log
            e.printStackTrace();

            throw new InternalServerErrorException("Error uploading image");
        }
    }
    
    public void uploadThumbnail(String path, ByteArrayOutputStream buffer, int width, int height) throws IOException {
        ByteArrayOutputStream thumbBuffer = new ByteArrayOutputStream();
        BufferedImage thumb = ImageIO.read(new ByteArrayInputStream(buffer.toByteArray()));
        thumb = Scalr.resize(thumb, Method.ULTRA_QUALITY,
                thumb.getHeight() < thumb.getWidth() ? Mode.FIT_TO_HEIGHT : Mode.FIT_TO_WIDTH,
                Math.max(width, height), Math.max(width, height), Scalr.OP_ANTIALIAS);
        thumb = Scalr.crop(thumb, width, height);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Needed see javadoc
        param.setCompressionQuality(1.0F); // Highest quality
        writer.setOutput(ImageIO.createImageOutputStream(thumbBuffer));
        writer.write(thumb);
        
        if (path.lastIndexOf('.') != -1) {
            path = path.substring(0, path.lastIndexOf('.'));
        }
        
        super.put(path + "." + width + "x" + height + ".jpg", new ByteArrayInputStream(thumbBuffer.toByteArray()),
                Long.valueOf(thumbBuffer.size()));
    }
}
