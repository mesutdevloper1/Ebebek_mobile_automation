Feature: E-bebek alışveriş senaryosu

  Scenario: Kategori seçimi ve ürün ekleme
    * open app
    * wait "20" second
    * click "Categories_Button"
    * click "Clothing_&_textil_Categories"
    * click "Clothing_&_textil_Filter_button"
    * click "Clothing_&_textil_Filter_price_Avarage_button"
    * click "Clothing_&_textil_Filter_price_Avarage_50-100"
    * click "apply_button"
    * click "see_product_buttonn"
    * click "Sorting_button"
    * click "Sorting_most_reviewed_button"
    * click "Basket_button"
    * i see "69,99" text
    * click "Basket_product_plus_button"
    * i cant see "69,99" text

