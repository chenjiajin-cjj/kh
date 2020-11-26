package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
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
     * 列表查询
     * @param pagePars
     * @return
     */
    PageData<Product> search(TablePagePars pagePars,String factoryId);
}
