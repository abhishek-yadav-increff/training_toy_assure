package com.increff.ui.dto;

import java.util.List;
import com.increff.ui.dto.helper.BrandDtoHelper;
import com.increff.ui.dto.helper.CommonsHelper;
import com.increff.ui.model.BrandData;
import com.increff.ui.model.BrandForm;
import com.increff.ui.pojo.BrandPojo;
import com.increff.ui.service.ApiException;
import com.increff.ui.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BrandDto
 */
@Service
public class BrandDto {

    @Autowired
    private BrandService brandService;

    public void add(BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo = BrandDtoHelper.convert(brandForm);
        brandPojo = BrandDtoHelper.normalize(brandPojo);
        brandService.add(brandPojo);
    }

    public BrandData get(int id) throws ApiException {
        BrandPojo brandPojo = brandService.get(id);
        return BrandDtoHelper.convert(brandPojo);
    }

    public List<BrandData> getAll() throws ApiException {
        List<BrandPojo> brandPojos = brandService.getAll();
        return BrandDtoHelper.convert(brandPojos);
    }

    public List<BrandData> getByCategory(String category) throws ApiException {
        category = CommonsHelper.normalize(category);
        List<BrandPojo> brandPojos = brandService.getByCategory(category);
        return BrandDtoHelper.convert(brandPojos);
    }

    public List<BrandData> getByBrand(String brand) throws ApiException {
        brand = CommonsHelper.normalize(brand);
        List<BrandPojo> brandPojos = brandService.getByBrand(brand);
        return BrandDtoHelper.convert(brandPojos);
    }

    public List<BrandData> getListByBrandAndCategory(String brand, String category)
            throws ApiException {
        if (category != null)
            category = CommonsHelper.normalize(category);
        if (brand != null)
            brand = CommonsHelper.normalize(brand);
        List<BrandPojo> brandPojos = brandService.getListByBrandAndCategory(brand, category);
        return BrandDtoHelper.convert(brandPojos);
    }

    public BrandPojo getByBrandAndCategory(BrandForm brandForm) throws ApiException {
        brandForm = BrandDtoHelper.normalize(brandForm);
        return brandService.getByBrandAndCategory(brandForm.getBrand(), brandForm.getCategory());
    }

    public void update(int id, BrandForm f) throws ApiException {
        BrandPojo p = BrandDtoHelper.convert(f);
        p = BrandDtoHelper.normalize(p);
        brandService.update(id, p);
    }
}
