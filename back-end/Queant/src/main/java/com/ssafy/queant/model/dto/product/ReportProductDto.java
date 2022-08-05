package com.ssafy.queant.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReportProductDto {
    private int reportProductId;
    @Schema(description = "이메일 주소")
    private String memberEmail;
    private String bankName;
    private String productName;
    private String referenceData;
    private boolean isDeposit;
    private String etc;
    private boolean isUpdated;
}
