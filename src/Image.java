/**
 * This class is mainly responsible for processing a group of images 
 * and converting them into gray scale images respectively.
 * @author Asad Zia
 * @version 1.0
 */

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;

public class Image 
{
	/**
	 * A function which converts a pixel into a grayscale pixel by taking an average of the R, G and B values.
	 * @param pixel
	 * @return result
	 */
	public static int convertToGrayPixel (int pixel)
	{
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		
		/* taking average */
		int avg = (red + green + blue) / 3;
		int result = (avg << 16) | (avg << 8) | avg;
		
		return result;
	}
	
	/**
	 * The function processes an image and  converts into grayscale format.
	 * @param image
	 * @return newImage
	 */
	public static BufferedImage processImage (BufferedImage image) 
	{
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < width; ++i) 
		{
			for (int j = 0; j < height; ++j)
			{
				int pixel = image.getRGB(i, j);				
				int result = convertToGrayPixel(pixel);
				newImage.setRGB(i, j, result);
			}
		}
		return newImage;
	}
	
	/**
	 * A function for finding the extension of the image file.
	 * Has to be updated to check for more types of image extensions.
	 * @param file
	 * @return ext
	 */
	public static String processFileExtension (File file)
	{
		String ext = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
		
		if (ext.equals("jpg"))
		{
			return "JPEG";
		}
		else if (ext.equals("png"))
		{
			return "PNG";
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * The function which implements the conversion of normal images to gray scale images.
	 * @throws IOException
	 * @throws SecurityException
	 * @throws NullPointerException
	 */
	public static void Run () throws IOException, SecurityException, NullPointerException
	{
		try 
		{
			File theDir = new File("Resultant Data");

			// if the directory does not exist, create it
			if (!theDir.exists()) 
			{
			    System.out.println("Creating directory: " + "Resultant Data");
			    boolean result = false;

			    try
			    {
			        theDir.mkdir();
			        result = true;
			    } 
			    catch (SecurityException se)
			    {
			        se.printStackTrace();
			    }        
			    if(result) 
			    {    
			        System.out.println(theDir.getName());  
			    }
			}
			
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);
			int ret = chooser.showOpenDialog(null);
			
			if (ret == JFileChooser.APPROVE_OPTION)
			{
				File [] inputImages = chooser.getSelectedFiles();
				
				for (int i = 0; i < inputImages.length; ++i)
				{
					BufferedImage image = ImageIO.read(inputImages[i]);
					BufferedImage newImage = processImage(image);
					File output = new File(theDir.getName() + "/Gray-" + inputImages[i].getName());
					ImageIO.write(newImage, processFileExtension(inputImages[i]), output);
				}
			}
			else
			{
				System.out.println("No images selected. Exiting Program.");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException r)
		{
			r.printStackTrace();
			System.out.println("Not a valid image.");
			Run();
		}
	}
	
	/**
	 * The driver method of the class
	 * @param args
	 * @throws IOException 
	 * @throws NullPointerException 
	 * @throws SecurityException 
	 */
	public static void main (String[] args) throws SecurityException, NullPointerException, IOException
	{
		Run();
	}
}