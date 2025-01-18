import com.example.webapp.dao.impl.ImageDaoImpl;
import com.example.webapp.model.Image;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageDaoTest {

    private static ImageDaoImpl imageDao;
    private static Image testImage;

    @BeforeAll
    static void setup() {
        imageDao = ImageDaoImpl.getInstance();

        testImage = Image.builder()
                .contentType("image/jpeg")
                .bytes(new byte[]{1, 2, 3})
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test save image")
    void testSaveImage() {
        imageDao.save(testImage);
        assertNotNull(testImage.getId(), "Image ID must not be null after save");
    }

    @Test
    @Order(2)
    @DisplayName("Test find image by ID")
    void testFindImageById() {
        Optional<Image> foundImage = imageDao.findById(testImage.getId());
        assertTrue(foundImage.isPresent(), "Image must be found by ID");
    }
}
