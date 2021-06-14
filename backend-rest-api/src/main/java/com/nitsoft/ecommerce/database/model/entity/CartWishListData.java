package com.nitsoft.ecommerce.database.model.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;

@Data
@Builder
public class CartWishListData {
    private Long productId;
    private Integer quantity;
    private Product productDetails;

    public static void main(String[] args){
        try{
            File inputFile = new File("/Users/vipulsharma/IdeaProjects/e-com/backend-rest-api/src/main/java/com/nitsoft/ecommerce/database/model/Screenshot 2020-11-17 at 11.36.57 AM.png");
            byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
            String imageString = Base64.getEncoder()
                    .encodeToString(fileContent);
            System.out.println(imageString);

            byte[] decodedBytes = Base64
                    .getDecoder()
                    .decode(imageString);
            File outputFile = new File("/Users/vipulsharma/IdeaProjects/e-com/backend-rest-api/src/main/java/com/nitsoft/ecommerce/database/model/Screen shot copy.png");
            FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
            BufferedImage img = ImageIO.read(new File("files/img/TestImage.png"));
            //String imageString1 = ImageUtil.encodeToString(img,"png");
            //BufferedImage img2 =  ImageUtil.decodeToImage(imageString1);
            System.out.println(img.equals(img));
        }catch (Exception ex){

        }
}
}
