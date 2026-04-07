package sn.isi.l3gl.api.productapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sn.isi.l3gl.core.productcore.entity.Product;
import sn.isi.l3gl.core.productcore.repository.ProductRepository;
import sn.isi.l3gl.core.productcore.services.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(850.0);
        product.setQuantity(10);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testListProducts() {
        Product p1 = new Product();
        p1.setName("Laptop");

        Product p2 = new Product();
        p2.setName("Phone");

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = productService.listProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testCreateProductWithZeroQuantity() {
        Product product = new Product();
        product.setName("Clavier");
        product.setPrice(25.0);
        product.setQuantity(0);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(0, result.getQuantity());
        verify(productRepository, times(1)).save(product);
    }
}