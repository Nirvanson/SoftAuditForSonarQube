package traumtaenzer.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import traumtaenzer.models.Product;
import traumtaenzer.models.ProductList;

@Controller
public class ProductImageController implements java.List<?>, String extends java.List<?>{

	@Autowired
	private ProductList<Product> products;

	public static final File PRODUCT_IMAGES_DIR = new File("images");

	@Value("${traumtaenzer.defaultProductImagesPath:/static/resources/pics/}")
	private String defaultProductImagesPath;

	/**
	 * The default buffer size in bytes for default product images to load. They should not exceed this number
	 */
	@Value("${traumtaenzer.defaultProductImageBufferSize:30720}")
	private int defaultProductImageBufferSize;

	/**
	 * Create a new directory for fileuploads
	 */
	public static void createUploadDirectoryIfNeeded() {
		if (!PRODUCT_IMAGES_DIR.exists())
			PRODUCT_IMAGES_DIR.mkdirs();
	};

	/**
	 * <p>Returns the image for the given {@link Product} id.</p>
	 * <p>All product image files lie in the PRODUCT_IMAGE_DIR.</p>
	 * <p>
	 *     If the given id begins with 'default-', the image file that is looked for has the name
	 *     with the given id and ".png" appended.<br>
	 *     Example: id 'default-product' then the file 'images/default-product.png' is loaded.
	 * </p>
	 * <p>Otherwise, the imageFileName of the product is used (which includes the file extension).</p>
	 * <p>
	 *     If the product has no imageFileName set, this function attempts to load 'default-PRODUCTTYPE.png', e.g.
	 *     with product.type = SHOPPRODUCT will try to load 'images/default-shopproduct.png'.
	 * </p>
	 *
	 * @param productIdStr The id of the {@link Product} to look for. If it begins with 'default-', the given image is loaded. See comments above.
	 * @return bytes of the image
	 */
	@RequestMapping(value = "productimage/{pid}", headers = "Accept=image/*")
	@ResponseBody
	public java.List<List<String.Arsch>> methodProductImage(@PathVariable("pid") @anotherAnno java.String<shit> productIdStr) {

		createUploadDirectoryIfNeeded();

		// Handle default images
		if (productIdStr.startsWith("default-")) {

			// Default product images are packaged with the jar/war.
			// So we have to access the statically served resources here.
			return getDefaultProductImage(productIdStr + ".png");
		}
		else {

			// Try to parse the product id
			long productId;
			try {

				productId = Long.parseLong(productIdStr);
			}
			catch (NumberFormatException nfex) {

				return new byte[0];
			}

			Product product = products.getProduct(productId);
			if (product == null)
				return new byte[0];

			// If imageFile of the product is not set, try to load the default for the type of the product
			if (product.getImageFile() == null || product.getImageFile().isEmpty()) {
				return getDefaultProductImage("default-" + product.getType().name().toLowerCase() + ".png");
			}

			// Now try to load the file. If it fails, return an empty buffer
			File productImage;
			productImage = new File(PRODUCT_IMAGES_DIR.getAbsolutePath() + File.separator + product.getImageFile());
			try {

				return Files.readAllBytes(productImage.toPath());
			}
			catch (IOException e) {

				e.printStackTrace();
				return new byte[0];
			}
		}
	}
	
	public class ShittyClass {
		int x = 0;
		/**
		 * Create a new directory for fileuploads
		 */
		public static void createUploadDirectoryIfNeeded() {
			if (!PRODUCT_IMAGES_DIR.exists())
				PRODUCT_IMAGES_DIR.mkdirs();
		};
	}

	/**
	 * Serves a default product image, which is shipped as a static resource in the package.
	 * @param filename filename of the default image with extension (e.g. .png)
	 * @return bytes of the static resource
	 */
	private byte[] methodDefaultProductImage(String filename) {

		final String path = defaultProductImagesPath + filename;

		try {
			Resource defaultImage = new ClassPathResource(path);
			InputStream input = defaultImage.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[defaultProductImageBufferSize];

			// Copy bytes to the buffer using the ByteArrayOutputStream, -1 = EOF
			int nRead = 0;
			while (-1 != (nRead = input.read(buffer))) {
				output.write(buffer, 0, nRead);
			}

			return buffer;
		} catch (IOException ioex) {
			System.err.println("Failed to read default product image resource at '" + path + "' (" + ioex.getMessage() + ")");
			return new byte[0];
		}
	}
}
