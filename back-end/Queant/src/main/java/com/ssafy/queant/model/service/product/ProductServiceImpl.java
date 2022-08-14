package com.ssafy.queant.model.service.product;

import com.querydsl.core.Tuple;
import com.ssafy.queant.model.dto.product.*;
import com.ssafy.queant.model.entity.product.*;
import com.ssafy.queant.model.repository.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ReportProductRepository reportProductRepository;
    private final ProductRepository productRepository;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final BankRepository bankRepository;
    private final JoinwayRepository joinwayRepository;
    private final ConditionsRepository conditionsRepository;
    private final OptionsRepository optionsRepository;
    private final TraitSetRepository traitSetRepository;
    private final ModelMapper modelMapper;
    private final ConditionsService conditionsService;
    private final OptionsService optionsService;
    private final JoinwayService joinwayService;
    private final TraitSetService traitSetService;


    @Override
    public List<ProductDto> findByBankId(int bankId) {
        List<Tuple> list = productRepositoryImpl.findByBankIdAndIsEnabledTrue(bankId);

        Optional<Bank> bankResult = bankRepository.findByBankId(bankId);
        String bankName = bankResult.get().getBankName();

        List<ProductDto> result = new ArrayList<>();

        for (Tuple t : list) {
            ProductDto dto = modelMapper.map(t.get(0, Product.class), ProductDto.class);
            dto.setBankName(bankName);
            dto.setBaseRate(t.get(1, Float.class));
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<ProductDto> findByNameContaining(String name) {
        List<Tuple> list = productRepositoryImpl.findByIsEnabledTrueAndNameContaining(name);
        List<ProductDto> result = new ArrayList<>();

        for (Tuple t : list) {
            ProductDto dto = modelMapper.map(t.get(0, Product.class), ProductDto.class);
            dto.setBaseRate(t.get(1, Float.class));

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
        List<JoinwayDto> joinway = joinwayService.findByProductId(productId);

        //Conditions
        List<ConditionsDto> conditions = conditionsService.findByProductId(productId);

        //Options
        List<OptionsDto> options = optionsService.findByProductId(productId);

        List<TraitSetDto> traitSet = traitSetService.findByProductId(productId);

        ProductDetailDto productDetailDto = ProductDetailDto.builder()
                .product(product)
                .joinway(joinway)
                .conditions(conditions)
                .options(options)
                .traitSet(traitSet)
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
    public String reportStatusToUpdated(int reportProductId) {
        //report를 실제로 삭제하는 것이 아닌 isUpdated를 true로
        Optional<ReportProduct> result =
                reportProductRepository.findByReportProductId(reportProductId);

        result.orElseThrow(() -> new NoSuchElementException("잘못된 제보 번호입니다."));

        ReportProduct reportProduct = result.get();
        reportProduct.setUpdated(true);

        reportProductRepository.save(reportProduct);

        return reportProduct.getBankName();
    }

    @Override
    //public void updateReportToProduct(int reportProductId, ProductDetailDto productDetail) {
    public void updateReportToProduct(UpdateDetailDto updateDetailDto) {

        UpdateProductDto product = updateDetailDto.getProduct();
        String bankName = reportStatusToUpdated(product.getReportId());

        //은행 이름을 통한 ID 설정 및 product 테이블에 추가
        int BankID = bankRepository.searchBankID(bankName);
        Product prd = modelMapper.map(product, Product.class);
        prd.setBankId(BankID);
        productRepository.save(prd);

        //product 테이블 추가 후 product_id를 각각 가져와야한다.
        int productID = productRepository.lastInsertId();

        List<OptionsDto> options = updateDetailDto.getOptions();
        List<TraitSetDto> traits = updateDetailDto.getTraitSet();
        List<ConditionsDto> conditions = updateDetailDto.getConditions();
        List<JoinwayDto> joinway = updateDetailDto.getJoinway();

        //traits 테이블에 추가
        for (TraitSetDto t : traits) {
            t.setProductId(productID);
            TraitSet tr = modelMapper.map(t, TraitSet.class);
            traitSetRepository.save(tr);
        }

        //options 테이블에 추가
        for (OptionsDto o : options) {
            o.setProductId(productID);
            Options opt = modelMapper.map(o, Options.class);
            optionsRepository.save(opt);
        }

        //conditions 테이블에 추가
        for (ConditionsDto c : conditions) {
            c.setProductId(productID);
            Conditions con = modelMapper.map(c, Conditions.class);
            conditionsRepository.save(con);
        }

        //joinway 테이블에 추가
        for (JoinwayDto j : joinway) {
            j.setProductId(productID);
            Joinway join = modelMapper.map(j, Joinway.class);
            joinwayRepository.save(join);
        }
    }
}
