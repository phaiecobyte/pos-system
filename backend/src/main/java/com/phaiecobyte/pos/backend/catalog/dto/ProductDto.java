package com.phaiecobyte.pos.backend.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDto() {

    @Schema(name = "ProductRequest", description = "ទិន្នន័យសម្រាប់បង្កើត ឬកែប្រែទំនិញ")
    public record Req(
            @Schema(description = "លេខកូដបាកូដ ឬ SKU", example = "8850001234567")
            @NotBlank(message = "លេខកូដបាកូដមិនអាចទទេបានទេ")
            String code,

            @Schema(description = "ឈ្មោះផលិតផល", example = "កូកាកូឡា កំប៉ុង")
            @NotBlank(message = "ឈ្មោះផលិតផលមិនអាចទទេបានទេ")
            String name,

            @Schema(description = "ព័ត៌មានលម្អិតពីផលិតផល", example = "ភេសជ្ជៈកំប៉ុងចំណុះ 330ml")
            String description,

            @Schema(description = "តម្លៃលក់", example = "0.75")
            @NotNull(message = "តម្លៃត្រូវតែកំណត់")
            BigDecimal price,

            @Schema(description = "កំណត់ថាតើទំនិញនេះត្រូវកាត់ស្តុកដែរឬទេ (True សម្រាប់ទំនិញរាយ, False សម្រាប់ម្ហូប)", example = "true")
            boolean stockable,

            @Schema(description = "លេខសម្គាល់ប្រភេទ (Category ID)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @NotNull(message = "ត្រូវតែជ្រើសរើសប្រភេទ (Category)")
            UUID categoryId,

            @Schema(description = "តំណភ្ជាប់រូបភាពផលិតផល", example = "https://example.com/images/coca-cola.png")
            String imageUrl
    ) {}

    @Schema(name = "ProductResponse", description = "ទិន្នន័យទំនិញដែលបញ្ជូនទៅកាន់ Frontend")
    public record Res(
            @Schema(description = "លេខសម្គាល់ទំនិញ", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID id,
            @Schema(description = "លេខកូដបាកូដ", example = "8850001234567")
            String code,
            @Schema(description = "ឈ្មោះផលិតផល", example = "កូកាកូឡា កំប៉ុង")
            String name,
            @Schema(description = "ព័ត៌មានលម្អិត", example = "ភេសជ្ជៈកំប៉ុងចំណុះ 330ml")
            String description,
            @Schema(description = "តម្លៃលក់", example = "0.75")
            BigDecimal price,
            @Schema(description = "ស្ថានភាពគ្រប់គ្រងស្តុក", example = "true")
            boolean stockable,
            @Schema(description = "ចំនួនស្តុកបច្ចុប្បន្ន", example = "150")
            Integer currentStock,
            @Schema(description = "ស្ថានភាពសកម្ម", example = "true")
            boolean active,
            @Schema(description = "តំណភ្ជាប់រូបភាព", example = "https://example.com/images/coca-cola.png")
            String imageUrl,
            @Schema(description = "លេខសម្គាល់ប្រភេទ (Category ID)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            UUID categoryId,
            @Schema(description = "ឈ្មោះប្រភេទ", example = "ភេសជ្ជៈ")
            String categoryName,
            @Schema(description = "កាលបរិច្ឆេទបង្កើត", example = "2026-05-18T10:42:00")
            LocalDateTime createdAt
    ) {}
}