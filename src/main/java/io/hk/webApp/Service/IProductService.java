package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Tools.TablePagePars;

public interface IProductService {
    /**
     * 添加商品
     * @param product
     * @return
     */
    boolean addProduct(Product product);

    /**
     * 修改商品
     * @param product
     * @return
     */
    boolean updateProduct(Product product);

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    boolean deleteProductById(String id);

    /**
     * 列表查询
     * @param pagePars
     * @return
     */
    PageData<Product> search(TablePagePars pagePars);

    /**
     * 根据id查询单个
     * @param id
     * @return
     */
    Product searchById(String id);
}
