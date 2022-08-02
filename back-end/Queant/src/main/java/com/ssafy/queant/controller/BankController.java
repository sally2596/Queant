package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.product.BankDto;
import com.ssafy.queant.model.dto.product.BankResponseDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.service.BankService;
import com.ssafy.queant.model.service.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;
    private final ProductService productService;

    @ApiResponses({
            @ApiResponse(code = 200, message="모든 은행정보를 가져왔습니다."),
    })
    @Operation(summary = "모든 은행 정보 받아옴")
    @GetMapping()
    public ResponseEntity<?> getAllBank() throws Exception {
        return new ResponseEntity<List<BankDto>>(bankService.findAll(),HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="은행 정보와 해당하는 은행의 상품 조회에 성공했습니다."),
            @ApiResponse(code = 404, message="은행 정보가 존재하지 않습니다"),
    })
    @Operation(summary = "은행에 따른 정보 받아옴")
    @GetMapping("/{bankId}")
    public ResponseEntity<?> getBankByBankId(@PathVariable("bankId") int bankId) throws Exception {
        BankDto bankDto =bankService.findByBankId(bankId);
        if(bankDto==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<ProductDto> productDtoList = productService.findByBankId(bankId);

        BankResponseDto responseDto = BankResponseDto.builder()
                .bank(bankDto)
                .productList(productDtoList)
                .build();

        return new ResponseEntity<BankResponseDto>(responseDto,HttpStatus.OK);
    }
}
