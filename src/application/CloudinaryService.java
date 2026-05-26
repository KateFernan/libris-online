package application;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

public class CloudinaryService {

    private static final Cloudinary cloudinary =
        new Cloudinary(ObjectUtils.asMap(
            "cloud_name", System.getenv("CLOUDINARY_NAME"),
            "api_key", System.getenv("CLOUDINARY_KEY"),
            "api_secret", System.getenv("CLOUDINARY_SECRET")
        ));

    public static String uploadPDF(File file) {
        try {

            Map uploadResult = cloudinary.uploader().upload(
                file,
                ObjectUtils.asMap(
                    "resource_type", "raw"
                )
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}