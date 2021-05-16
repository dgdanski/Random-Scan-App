# Random-Scan-App
Zrobiona została część A.</br>
Punkt 6. nie był jasno sformułowany i nie został z tego powodu w pełni zrealizowany. W ramach extrasów z części B, dorzucam splash screen.</br>
Apka ma celowo dość szorstki wygląd, skupiłem się na funkcjonalności.</br></br>
Part A (Obligatory):
1. Every 5 sec. some producer will produce the next value with random Barcode value, random Barcode type and random
color combination from defined lists:
- private val barcodes = listOf("PK01", "BARCODE_A", "4354834", "PO14785-20171215")</br>
- private val types = listOf("EAN8", "UPCE", "EAN13", "I25", "QRCODE", "CODE128")</br>
- private val colors = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")

2. Producer should emit new random values ONLY if the app is in the foreground!
3. Master Screen of the app will be a list of views represented by:
first TextView (text = barcode value, textColor = color),
second TextView with barcode type
third TextView with Date & Time when random value was generated.
4. The list should be sorted alphabetically by the barcode value
5. When new item is emitted, it should be added to the sorted list in the proper place
6. On item click, the Toolbar should change its color to the selected one and the text on it should change (barcode value
as a title, and barcode type as a subtitle).
7. A list of emitted items should be saved on the device.
