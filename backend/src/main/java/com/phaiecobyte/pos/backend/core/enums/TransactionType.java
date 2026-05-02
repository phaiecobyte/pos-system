package com.phaiecobyte.pos.backend.core.enums;

public enum TransactionType {
    IN,         // បញ្ចូលស្តុក (ទិញចូល)
    OUT,        // ដកស្តុកចេញ (ខូចខាត, ផុតកំណត់)
    SALE,       // លក់ចេញ (ប្រព័ន្ធនឹងកាត់ស្វ័យប្រវត្តិពេលគិតលុយ)
    ADJUSTMENT  // កែតម្រូវស្តុក (ពេលរាប់ស្តុកជាក់ស្តែង)
}