package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.*;
import com.ssafy.queant.model.entity.product.*;
import com.ssafy.queant.model.repository.product.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ReportProductRepository reportProductRepository;
    private final ProductRepository productRepository;
    private final BankRepository bankRepository;
    private final JoinwayRepository joinwayRepository;
    private final ConditionsRepository conditionsRepository;
    private final OptionsRepository optionsRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ReportProductRepository reportProductRepository, ProductRepository productRepository, BankRepository bankRepository, JoinwayRepository joinwayRepository, ConditionsRepository conditionsRepository, OptionsRepository optionsRepository, ModelMapper modelMapper) {
        this.reportProductRepository = reportProductRepository;
        this.productRepository = productRepository;
        this.bankRepository = bankRepository;
        this.joinwayRepository = joinwayRepository;
        this.conditionsRepository = conditionsRepository;
        this.optionsRepository = optionsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> findByBankId(int bankId) {
        List<Product> list = productRepository.findByBankIdAndIsEnabledTrue(bankId);

        Optional<Bank> bankResult = bankRepository.findByBankId(bankId);
        String bankName = bankResult.get().getBankName();

        List<ProductDto> result = new ArrayList<>();

        for (Product p : list) {
            ProductDto dto = modelMapper.map(p, ProductDto.class);
            dto.setBankName(bankName);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<ProductDto> findByNameContaining(String name) {
        List<Product> list = productRepository.findByIsEnabledTrueAndNameContaining(name);
        List<ProductDto> result = new ArrayList<>();

        for (Product p : list) {
            ProductDto dto = modelMapper.map(p, ProductDto.class);

            Optional<Bank> bankResult = bankRepository.findByBankId(dto.getBankId());
            dto.setBankName(bankResult.get().getBankName());
            result.add(dto);
        }
        return result;
    }

    @Override
    public ProductDetailDto findByProductId(int productId) {

        // product
        Optional<Product> result = productRepository.findByProductId(productId);
        if (!result.isPresent()) return null;

        Product resultProduct = result.get();
        ProductDto product = modelMapper.map(resultProduct, ProductDto.class);

        // bankName 설정
        Optional<Bank> bankResult = bankRepository.findByBankId(product.getBankId());
        product.setBankName(bankResult.get().getBankName());

        //joinway
        List<Joinway> resultJoin = joinwayRepository.findByProductId(productId);
        List<JoinwayDto> joinway = new ArrayList<>();
        if (resultJoin.size() > 0) {
            for (Joinway j : resultJoin) {
                joinway.add(modelMapper.map(j, JoinwayDto.class));
            }
        }
        //Conditions
        List<Conditions> resultConditions = conditionsRepository.findByProductId(productId);
        List<ConditionsDto> conditions = new ArrayList<>();
        if (resultConditions.size() > 0) {
            for (Conditions c : resultConditions) {
                conditions.add(modelMapper.map(c, ConditionsDto.class));
            }
        }

        //Options
        List<Options> resultOptions = optionsRepository.findByProductId(productId);
        List<OptionsDto> options = new ArrayList<>();
        if (resultOptions.size() > 0) {
            for (Options o : resultOptions) {
                options.add(modelMapper.map(o, OptionsDto.class));
            }
        }

        ProductDetailDto productDetailDto = ProductDetailDto.builder()
                .product(product)
                .joinway(joinway)
                .conditions(conditions)
                .options(options)
                .build();

        return productDetailDto;
    }


    @Override
    public ProductDto registProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

//    @Override
//    public ProductDto updateToProovedProduct(int productId) {
//        Optional<Product> result = productRepository.findByProductId(productId);
//
//        if (result.isPresent()) {
//            Product product = result.get();
//            product.setEnabled(true);
//            productRepository.save(product);
//            return modelMapper.map(product, ProductDto.class);
//        }
//
//        return null;
//    }

    @Override
    public List<ProductDto> findByIsEnabledFalse() {
        List<Product> productList = productRepository.findByIsEnabledFalse();
        List<ProductDto> productDtoList = new ArrayList<>();
        if (productList.size() > 0) {
            for (Product p : productList) {
                productDtoList.add(modelMapper.map(p, ProductDto.class));
            }
        }
        return productDtoList;
    }

    @Override
    public void updateReportToProduct(int reportProductId) {

        //Product data =

//        Optional<ReportProduct> result =
//                reportProductRepository.findByReportProductId(reportProductId);
//
//        result.orElseThrow(() -> new NoSuchElementException("없는 제보 정보입니다."));
//
//        ReportProduct reportProduct = result.get();
//        reportProduct.setUpdated(true);

//        int bankID = bankRepository.searchBankID(reportProduct.getBankName());
//
//        ReportProduct saved = reportProductRepository.save(reportProduct);
    }
}
