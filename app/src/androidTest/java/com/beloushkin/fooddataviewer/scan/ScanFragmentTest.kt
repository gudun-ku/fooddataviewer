package com.beloushkin.fooddataviewer.scan

import android.Manifest
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.R.id.productView
import com.beloushkin.fooddataviewer.component
import com.beloushkin.fooddataviewer.di.TestComponent
import com.beloushkin.fooddataviewer.utils.TestIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@LargeTest
class ScanFragmentTest {

    @Rule
    @JvmField
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.CAMERA
    )

    @BeforeClass
    fun setupClass() {
        throw RuntimeException("Sorry dude, you won't find any test!")
    }

    @Test
    fun views(){
        val scenario = launchFragmentInContainer<ScanFragment>(
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
        var idlingResource: TestIdlingResource? = null

        scenario.onFragment {fragment ->
            idlingResource = ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)
            idlingResource!!.increment()

            (fragment.activity!!.component.frameProcessorOnSubscribe() as TestFrameProcessorOnSubscribe)
                .testFrame = getFrame(fragment.requireContext(), "coca-cola.jpg")

        }

        val mockResponse = MockResponse()
        mockResponse.setBody(json)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)
        onView(withId(productView)).check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource!!.countingIdlingResource)
    }

    private val mockWebServer = MockWebServer()

    private val json = """
        {
          "status": 1,
          "status_verbose": "product found",
          "code": "8888002076009",
          "product": {
            "additives_old_tags": [
              "en:e150a"
            ],
            "brands": "Coca-Cola",
            "nutrition_data": "on",
            "pnns_groups_1_tags": [
              "beverages",
              "known"
            ],
            "product_quantity": "320",
            "emb_codes_20141016": "",
            "debug_param_sorted_langs": [
              "en",
              "fr"
            ],
            "unknown_nutrients_tags": [
              
            ],
            "manufacturing_places": "Malaysia",
            "nutriscore_data": {
              "grade": "e",
              "sugars_value": 10.6,
              "fiber_points": 0,
              "saturated_fat": 0,
              "fruits_vegetables_nuts_colza_walnut_olive_oils": 0,
              "saturated_fat_ratio": 0,
              "proteins_points": 0,
              "fiber": 0,
              "is_fat": 0,
              "proteins": 0,
              "is_beverage": 1,
              "sugars_points": 8,
              "is_cheese": 0,
              "score": 14,
              "saturated_fat_ratio_value": 0,
              "sodium_points": 0,
              "saturated_fat_value": 0,
              "sugars": 10.6,
              "saturated_fat_points": 0,
              "fruits_vegetables_nuts_colza_walnut_olive_oils_value": 0,
              "energy_points": 6,
              "negative_points": 14,
              "sodium": 2,
              "is_water": 0,
              "saturated_fat_ratio_points": 0,
              "energy": 176,
              "sodium_value": 2,
              "energy_value": 176,
              "positive_points": 0,
              "proteins_value": 0,
              "fiber_value": 0,
              "fruits_vegetables_nuts_colza_walnut_olive_oils_points": 0
            },
            "image_ingredients_thumb_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/ingredients_en.12.100.jpg",
            "ingredients_from_or_that_may_be_from_palm_oil_n": 0,
            "additives_debug_tags": [
              
            ],
            "checkers_tags": [
              
            ],
            "pnns_groups_2": "Artificially sweetened beverages",
            "last_editor": "tacite",
            "nutrition_score_warning_fruits_vegetables_nuts_estimate_from_ingredients_value": 0,
            "ingredients_tags": [
              "en:carbonated-water",
              "en:water",
              "en:high-fructose-corn-syrup",
              "en:glucose",
              "en:fructose",
              "en:corn-syrup",
              "en:glucose-fructose-syrup",
              "en:sucrose",
              "en:sugar",
              "en:e150a",
              "en:e338",
              "en:flavouring",
              "en:caffeine"
            ],
            "nutrition_data_prepared": "",
            "traces_from_ingredients": "",
            "data_quality_warnings_tags": [
              "en:nutrition-value-under-0-01-g-salt",
              "en:nutrition-value-very-high-for-category-energy",
              "en:nutrition-value-very-high-for-category-carbohydrates",
              "en:nutrition-value-very-high-for-category-sugars"
            ],
            "completed_t": 1555473527,
            "labels_hierarchy": [
              "en:halal"
            ],
            "generic_name_fr": "",
            "serving_size": "320 ml",
            "data_quality_tags": [
              "en:ingredients-percent-analysis-ok",
              "en:nutrition-value-under-0-01-g-salt",
              "en:nutrition-value-very-high-for-category-energy",
              "en:nutrition-value-very-high-for-category-carbohydrates",
              "en:nutrition-value-very-high-for-category-sugars"
            ],
            "nutriscore_score": 14,
            "additives_old_n": 1,
            "ingredients_from_palm_oil_tags": [
              
            ],
            "last_image_t": 1555473506,
            "additives_n": 2,
            "nutrition_data_per": "100g",
            "expiration_date": "",
            "ingredients_text": "CARBONATED WATER, HIGH FRUCTOSE CORN SYRUP, SUCROSE, CARAMEL COLOUR, PHOSPHORIC ACID, FLAVOURINGS, CAFFEINE",
            "other_nutritional_substances_tags": [
              
            ],
            "ingredients_hierarchy": [
              "en:carbonated-water",
              "en:water",
              "en:high-fructose-corn-syrup",
              "en:glucose",
              "en:fructose",
              "en:corn-syrup",
              "en:glucose-fructose-syrup",
              "en:sucrose",
              "en:sugar",
              "en:e150a",
              "en:e338",
              "en:flavouring",
              "en:caffeine"
            ],
            "traces_from_user": "(fr) ",
            "no_nutrition_data": "",
            "last_image_dates_tags": [
              "2019-04-17",
              "2019-04",
              "2019"
            ],
            "stores": "",
            "creator": "openfoodfacts-contributors",
            "ingredients_that_may_be_from_palm_oil_tags": [
              
            ],
            "origins": "",
            "categories_hierarchy": [
              "en:beverages",
              "en:carbonated-drinks",
              "en:artificially-sweetened-beverages",
              "en:sodas",
              "en:diet-beverages",
              "en:colas",
              "en:diet-sodas",
              "en:diet-cola-soft-drink"
            ],
            "update_key": "ingredients20200329",
            "data_quality_bugs_tags": [
              
            ],
            "nutrition_grade_fr": "e",
            "nutriments": {
              "energy_value": 42,
              "energy_unit": "kcal",
              "sodium_value": 2,
              "sodium_serving": 0.0064,
              "energy-kcal_value": 42,
              "salt_value": 5,
              "nutrition-score-fr_100g": 14,
              "energy-kcal_serving": 563,
              "proteins_value": 0,
              "salt_unit": "mg",
              "fat_serving": 0,
              "sugars_unit": "g",
              "salt_100g": 0.005,
              "nova-group_100g": 4,
              "sodium": 0.002,
              "carbohydrates_unit": "g",
              "energy": 176,
              "energy_100g": 176,
              "salt": 0.005,
              "saturated-fat_serving": 0,
              "nova-group": 4,
              "nova-group_serving": 4,
              "saturated-fat": 0,
              "sugars_100g": 10.6,
              "fat_unit": "g",
              "saturated-fat_unit": "g",
              "carbohydrates_serving": 33.9,
              "energy-kcal_unit": "kcal",
              "fat": 0,
              "fat_value": 0,
              "sugars": 10.6,
              "proteins_100g": 0,
              "sugars_serving": 33.9,
              "carbohydrates_value": 10.6,
              "proteins_unit": "g",
              "saturated-fat_value": 0,
              "nutrition-score-fr": 14,
              "fat_100g": 0,
              "proteins_serving": 0,
              "sodium_100g": 0.002,
              "carbohydrates_100g": 10.6,
              "salt_serving": 0.016,
              "saturated-fat_100g": 0,
              "proteins": 0,
              "energy-kcal_100g": 176,
              "energy-kcal": 176,
              "sugars_value": 10.6,
              "fruits-vegetables-nuts-estimate-from-ingredients_100g": 0,
              "sodium_unit": "mg",
              "carbohydrates": 10.6,
              "energy_serving": 563
            },
            "labels_lc": "fr",
            "nova_groups_tags": [
              "en:4-ultra-processed-food-and-drink-products"
            ],
            "categories_lc": "fr",
            "traces": "",
            "data_sources": "App - yuka, Apps",
            "categories_tags": [
              "en:beverages",
              "en:carbonated-drinks",
              "en:artificially-sweetened-beverages",
              "en:sodas",
              "en:diet-beverages",
              "en:colas",
              "en:diet-sodas",
              "en:diet-cola-soft-drink"
            ],
            "states_hierarchy": [
              "en:to-be-checked",
              "en:complete",
              "en:nutrition-facts-completed",
              "en:ingredients-completed",
              "en:expiration-date-to-be-completed",
              "en:packaging-code-to-be-completed",
              "en:characteristics-completed",
              "en:categories-completed",
              "en:brands-completed",
              "en:packaging-completed",
              "en:quantity-completed",
              "en:product-name-completed",
              "en:photos-validated",
              "en:photos-uploaded"
            ],
            "ingredients_from_palm_oil_n": 0,
            "image_nutrition_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/nutrition_en.15.400.jpg",
            "countries_tags": [
              "en:algeria",
              "en:singapore"
            ],
            "languages_tags": [
              "en:english",
              "en:1"
            ],
            "serving_quantity": "320",
            "image_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.400.jpg",
            "countries_hierarchy": [
              "en:algeria",
              "en:singapore"
            ],
            "additives_original_tags": [
              "en:e150c",
              "en:e338"
            ],
            "compared_to_category": "en:diet-cola-soft-drink",
            "obsolete_since_date": "",
            "pnns_groups_2_tags": [
              "artificially-sweetened-beverages",
              "known"
            ],
            "interface_version_created": "20120622",
            "informers_tags": [
              "openfoodfacts-contributors",
              "yuka.VC9nZ01Za2VwY0pYb2ZNeS9nT081dUJmK3BLblVVenFPKzRMSWc9PQ",
              "kiliweb",
              "bcd4e6",
              "bredowmax"
            ],
            "created_t": 1432938553,
            "nova_group": 4,
            "additives_tags": [
              "en:e150c",
              "en:e338"
            ],
            "cities_tags": [
              
            ],
            "ingredients_text_fr": "",
            "allergens_from_ingredients": "",
            "languages_hierarchy": [
              "en:english"
            ],
            "unknown_ingredients_n": 0,
            "nova_group_tags": [
              "not-applicable"
            ],
            "entry_dates_tags": [
              "2015-05-30",
              "2015-05",
              "2015"
            ],
            "image_thumb_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.100.jpg",
            "image_front_small_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.200.jpg",
            "traces_tags": [
              
            ],
            "allergens": "",
            "generic_name": "",
            "labels": "Halal",
            "pnns_groups_1": "Beverages",
            "stores_tags": [
              
            ],
            "allergens_tags": [
              
            ],
            "complete": 1,
            "amino_acids_tags": [
              
            ],
            "ingredients": [
              {
                "text": "CARBONATED WATER",
                "percent_min": 14.2857142857143,
                "vegan": "yes",
                "id": "en:carbonated-water",
                "vegetarian": "yes",
                "rank": 1,
                "percent_max": 100
              },
              {
                "percent_min": 0,
                "text": "HIGH FRUCTOSE CORN SYRUP",
                "vegan": "yes",
                "rank": 2,
                "vegetarian": "yes",
                "id": "en:high-fructose-corn-syrup",
                "percent_max": 50
              },
              {
                "vegetarian": "yes",
                "rank": 3,
                "id": "en:sucrose",
                "percent_max": 33.3333333333333,
                "text": "SUCROSE",
                "percent_min": 0,
                "vegan": "yes"
              },
              {
                "percent_max": 25,
                "vegetarian": "yes",
                "id": "en:e150a",
                "rank": 4,
                "vegan": "yes",
                "percent_min": 0,
                "text": "CARAMEL COLOUR"
              },
              {
                "vegan": "yes",
                "percent_min": 0,
                "text": "PHOSPHORIC ACID",
                "id": "en:e338",
                "vegetarian": "yes",
                "rank": 5,
                "percent_max": 20
              },
              {
                "vegan": "maybe",
                "text": "FLAVOURINGS",
                "percent_min": 0,
                "vegetarian": "maybe",
                "rank": 6,
                "id": "en:flavouring",
                "percent_max": 16.6666666666667
              },
              {
                "rank": 7,
                "id": "en:caffeine",
                "vegetarian": "yes",
                "percent_max": 14.2857142857143,
                "vegan": "yes",
                "text": "CAFFEINE",
                "percent_min": 0
              }
            ],
            "data_quality_info_tags": [
              "en:ingredients-percent-analysis-ok"
            ],
            "purchase_places": "Singapore",
            "generic_name_en": "",
            "nutrition_data_prepared_per": "100g",
            "editors": [
              ""
            ],
            "quantity": "320 ml",
            "nova_groups": "4",
            "origins_tags": [
              
            ],
            "vitamins_tags": [
              
            ],
            "image_nutrition_small_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/nutrition_en.15.200.jpg",
            "ingredients_n": 7,
            "amino_acids_prev_tags": [
              
            ],
            "sortkey": 301586018568,
            "completeness": 0.8,
            "allergens_hierarchy": [
              
            ],
            "ingredients_analysis_tags": [
              "en:palm-oil-free",
              "en:maybe-vegan",
              "en:maybe-vegetarian"
            ],
            "scans_n": 75,
            "minerals_tags": [
              
            ],
            "packaging_tags": [
              "can"
            ],
            "product_name_en": "Coke Classic",
            "brands_tags": [
              "coca-cola"
            ],
            "nutrition_grades_tags": [
              "e"
            ],
            "ingredients_original_tags": [
              "en:carbonated-water",
              "en:high-fructose-corn-syrup",
              "en:sucrose",
              "en:e150a",
              "en:e338",
              "en:flavouring",
              "en:caffeine"
            ],
            "product_name_fr": "",
            "codes_tags": [
              "code-13",
              "8888002076xxx",
              "888800207xxxx",
              "88880020xxxxx",
              "8888002xxxxxx",
              "888800xxxxxxx",
              "88880xxxxxxxx",
              "8888xxxxxxxxx",
              "888xxxxxxxxxx",
              "88xxxxxxxxxxx",
              "8xxxxxxxxxxxx"
            ],
            "image_nutrition_thumb_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/nutrition_en.15.100.jpg",
            "image_small_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.200.jpg",
            "countries_lc": "fr",
            "editors_tags": [
              "bredowmax",
              "bcd4e6",
              "yuka.VC9nZ01Za2VwY0pYb2ZNeS9nT081dUJmK3BLblVVenFPKzRMSWc9PQ",
              "kiliweb",
              "fixbot",
              "tacite",
              "openfoodfacts-contributors"
            ],
            "packaging": "Can",
            "nutrient_levels": {
              "sugars": "high",
              "saturated-fat": "low",
              "salt": "low",
              "fat": "low"
            },
            "nucleotides_prev_tags": [
              
            ],
            "product_name": "Coke Classic",
            "countries": "Algérie,Singapour",
            "code": "8888002076009",
            "emb_codes": "",
            "nutrition_score_warning_fruits_vegetables_nuts_estimate_from_ingredients": 1,
            "popularity_tags": [
              "top-100000-scans-2020",
              "top-90-percent-scans-2020",
              "top-95-percent-scans-2020",
              "top-50-tr-scans-2020",
              "top-100-tr-scans-2020",
              "top-500-tr-scans-2020",
              "top-1000-tr-scans-2020",
              "top-5000-tr-scans-2020",
              "top-10000-tr-scans-2020",
              "top-50000-tr-scans-2020",
              "top-100000-tr-scans-2020",
              "top-country-tr-scans-2020",
              "top-50000-scans-2019",
              "top-100000-scans-2019",
              "at-least-5-scans-2019",
              "at-least-10-scans-2019",
              "top-90-percent-scans-2019",
              "top-95-percent-scans-2019",
              "top-50000-fr-scans-2019",
              "top-100000-fr-scans-2019",
              "top-country-fr-scans-2019",
              "at-least-5-fr-scans-2019",
              "at-least-10-fr-scans-2019",
              "top-500-sg-scans-2019",
              "top-1000-sg-scans-2019",
              "top-5000-sg-scans-2019",
              "top-10000-sg-scans-2019",
              "top-50000-sg-scans-2019",
              "top-100000-sg-scans-2019",
              "top-10000-be-scans-2019",
              "top-50000-be-scans-2019",
              "top-100000-be-scans-2019",
              "top-5000-re-scans-2019",
              "top-10000-re-scans-2019",
              "top-50000-re-scans-2019",
              "top-100000-re-scans-2019",
              "top-5000-es-scans-2019",
              "top-10000-es-scans-2019",
              "top-50000-es-scans-2019",
              "top-100000-es-scans-2019",
              "top-50-vn-scans-2019",
              "top-100-vn-scans-2019",
              "top-500-vn-scans-2019",
              "top-1000-vn-scans-2019",
              "top-5000-vn-scans-2019",
              "top-10000-vn-scans-2019",
              "top-50000-vn-scans-2019",
              "top-100000-vn-scans-2019",
              "top-500-bg-scans-2019",
              "top-1000-bg-scans-2019",
              "top-5000-bg-scans-2019",
              "top-10000-bg-scans-2019",
              "top-50000-bg-scans-2019",
              "top-100000-bg-scans-2019",
              "top-500-ie-scans-2019",
              "top-1000-ie-scans-2019",
              "top-5000-ie-scans-2019",
              "top-10000-ie-scans-2019",
              "top-50000-ie-scans-2019",
              "top-100000-ie-scans-2019"
            ],
            "id": "8888002076009",
            "last_modified_t": 1586018568,
            "labels_tags": [
              "en:halal"
            ],
            "ingredients_text_with_allergens_en": "CARBONATED WATER, HIGH FRUCTOSE CORN SYRUP, SUCROSE, CARAMEL COLOUR, PHOSPHORIC ACID, FLAVOURINGS, CAFFEINE",
            "additives_prev_original_tags": [
              "en:e150a",
              "en:e338"
            ],
            "lc": "en",
            "nutrient_levels_tags": [
              "en:fat-in-low-quantity",
              "en:saturated-fat-in-low-quantity",
              "en:sugars-in-high-quantity",
              "en:salt-in-low-quantity"
            ],
            "minerals_prev_tags": [
              
            ],
            "nutrition_grades": "e",
            "nutrition_score_beverage": 1,
            "ingredients_text_with_allergens": "CARBONATED WATER, HIGH FRUCTOSE CORN SYRUP, SUCROSE, CARAMEL COLOUR, PHOSPHORIC ACID, FLAVOURINGS, CAFFEINE",
            "ingredients_that_may_be_from_palm_oil_n": 0,
            "nova_group_debug": " -- categories/en:sodas : 3 -- additives/en:e150c : 4",
            "interface_version_modified": "20190830",
            "ingredients_text_en": "CARBONATED WATER, HIGH FRUCTOSE CORN SYRUP, SUCROSE, CARAMEL COLOUR, PHOSPHORIC ACID, FLAVOURINGS, CAFFEINE",
            "traces_lc": "fr",
            "image_ingredients_small_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/ingredients_en.12.200.jpg",
            "categories": "Boissons,Boissons gazeuses,Boissons édulcorées,Sodas,Boissons light,Sodas au cola,Sodas light,Sodas au cola light",
            "last_edit_dates_tags": [
              "2020-04-04",
              "2020-04",
              "2020"
            ],
            "nucleotides_tags": [
              
            ],
            "_keywords": [
              "coke",
              "light",
              "coca-cola",
              "gazeuse",
              "au",
              "edulcoree",
              "cola",
              "halal",
              "soda",
              "classic",
              "boisson"
            ],
            "image_ingredients_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/ingredients_en.12.400.jpg",
            "link": "",
            "rev": 20,
            "traces_hierarchy": [
              
            ],
            "vitamins_prev_tags": [
              
            ],
            "obsolete": "",
            "last_modified_by": "tacite",
            "image_front_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.400.jpg",
            "image_front_thumb_url": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.100.jpg",
            "nutriscore_grade": "e",
            "unique_scans_n": 25,
            "correctors_tags": [
              "openfoodfacts-contributors",
              "yuka.VC9nZ01Za2VwY0pYb2ZNeS9nT081dUJmK3BLblVVenFPKzRMSWc9PQ",
              "bcd4e6",
              "fixbot",
              "bredowmax",
              "tacite"
            ],
            "lang": "en",
            "ingredients_n_tags": [
              "7",
              "1-10"
            ],
            "purchase_places_tags": [
              "singapore"
            ],
            "images": {
              "1": {
                "uploader": "openfoodfacts-contributors",
                "uploaded_t": 1432938553,
                "sizes": {
                  "100": {
                    "w": 91,
                    "h": 100
                  },
                  "400": {
                    "w": 214,
                    "h": 236
                  },
                  "full": {
                    "h": 236,
                    "w": 214
                  }
                }
              },
              "2": {
                "sizes": {
                  "100": {
                    "h": 100,
                    "w": 88
                  },
                  "400": {
                    "w": 352,
                    "h": 400
                  },
                  "full": {
                    "w": 1055,
                    "h": 1200
                  }
                },
                "uploaded_t": 1552546412,
                "uploader": "kiliweb"
              },
              "3": {
                "uploaded_t": 1555473390,
                "uploader": "bcd4e6",
                "sizes": {
                  "100": {
                    "h": 100,
                    "w": 38
                  },
                  "400": {
                    "w": 153,
                    "h": 400
                  },
                  "full": {
                    "w": 1447,
                    "h": 3793
                  }
                }
              },
              "4": {
                "sizes": {
                  "100": {
                    "h": 47,
                    "w": 100
                  },
                  "400": {
                    "h": 189,
                    "w": 400
                  },
                  "full": {
                    "w": 1611,
                    "h": 763
                  }
                },
                "uploaded_t": 1555473424,
                "uploader": "bcd4e6"
              },
              "5": {
                "uploaded_t": 1555473505,
                "uploader": "bcd4e6",
                "sizes": {
                  "100": {
                    "w": 100,
                    "h": 52
                  },
                  "400": {
                    "w": 400,
                    "h": 208
                  },
                  "full": {
                    "w": 1810,
                    "h": 941
                  }
                }
              },
              "nutrition_en": {
                "orientation": null,
                "normalize": null,
                "y1": null,
                "y2": null,
                "rev": "15",
                "geometry": "0x0-0-0",
                "sizes": {
                  "100": {
                    "h": 52,
                    "w": 100
                  },
                  "200": {
                    "h": 104,
                    "w": 200
                  },
                  "400": {
                    "h": 208,
                    "w": 400
                  },
                  "full": {
                    "w": 1810,
                    "h": 941
                  }
                },
                "ocr": 1,
                "x2": null,
                "x1": null,
                "imgid": "5",
                "angle": null,
                "white_magic": null
              },
              "ingredients_en": {
                "white_magic": null,
                "angle": null,
                "imgid": "4",
                "x1": null,
                "x2": null,
                "ocr": 1,
                "sizes": {
                  "100": {
                    "h": 47,
                    "w": 100
                  },
                  "200": {
                    "h": 95,
                    "w": 200
                  },
                  "400": {
                    "h": 189,
                    "w": 400
                  },
                  "full": {
                    "w": 1611,
                    "h": 763
                  }
                },
                "geometry": "0x0-0-0",
                "rev": "12",
                "y2": null,
                "normalize": null,
                "y1": null,
                "orientation": null
              },
              "front_en": {
                "rev": "9",
                "y2": null,
                "normalize": null,
                "y1": null,
                "sizes": {
                  "100": {
                    "h": 100,
                    "w": 38
                  },
                  "200": {
                    "h": 200,
                    "w": 76
                  },
                  "400": {
                    "h": 400,
                    "w": 153
                  },
                  "full": {
                    "w": 1447,
                    "h": 3793
                  }
                },
                "geometry": "0x0-0-0",
                "imgid": "3",
                "x2": null,
                "x1": null,
                "white_magic": null,
                "angle": null
              }
            },
            "emb_codes_tags": [
              
            ],
            "states_tags": [
              "en:to-be-checked",
              "en:complete",
              "en:nutrition-facts-completed",
              "en:ingredients-completed",
              "en:expiration-date-to-be-completed",
              "en:packaging-code-to-be-completed",
              "en:characteristics-completed",
              "en:categories-completed",
              "en:brands-completed",
              "en:packaging-completed",
              "en:quantity-completed",
              "en:product-name-completed",
              "en:photos-validated",
              "en:photos-uploaded"
            ],
            "selected_images": {
              "nutrition": {
                "display": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/nutrition_en.15.400.jpg"
                },
                "small": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/nutrition_en.15.200.jpg"
                },
                "thumb": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/nutrition_en.15.100.jpg"
                }
              },
              "front": {
                "small": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.200.jpg"
                },
                "thumb": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.100.jpg"
                },
                "display": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/front_en.9.400.jpg"
                }
              },
              "ingredients": {
                "display": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/ingredients_en.12.400.jpg"
                },
                "small": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/ingredients_en.12.200.jpg"
                },
                "thumb": {
                  "en": "https://static.openfoodfacts.org/images/products/888/800/207/6009/ingredients_en.12.100.jpg"
                }
              }
            },
            "allergens_from_user": "(fr) ",
            "languages": {
              "en:english": 5
            },
            "languages_codes": {
              "en": 5
            },
            "_id": "8888002076009",
            "photographers_tags": [
              "openfoodfacts-contributors",
              "kiliweb",
              "bcd4e6"
            ],
            "ingredients_debug": [
              "CARBONATED WATER",
              ",",
              null,
              null,
              null,
              " HIGH FRUCTOSE CORN SYRUP",
              ",",
              null,
              null,
              null,
              " SUCROSE",
              ",",
              null,
              null,
              null,
              " CARAMEL COLOUR",
              ",",
              null,
              null,
              null,
              " PHOSPHORIC ACID",
              ",",
              null,
              null,
              null,
              " FLAVOURINGS",
              ",",
              null,
              null,
              null,
              " CAFFEINE"
            ],
            "allergens_lc": "fr",
            "ingredients_ids_debug": [
              "carbonated-water",
              "high-fructose-corn-syrup",
              "sucrose",
              "caramel-colour",
              "phosphoric-acid",
              "flavourings",
              "caffeine"
            ],
            "manufacturing_places_tags": [
              "malaysia"
            ],
            "states": "en:to-be-checked, en:complete, en:nutrition-facts-completed, en:ingredients-completed, en:expiration-date-to-be-completed, en:packaging-code-to-be-completed, en:characteristics-completed, en:categories-completed, en:brands-completed, en:packaging-completed, en:quantity-completed, en:product-name-completed, en:photos-validated, en:photos-uploaded",
            "data_sources_tags": [
              "app-yuka",
              "apps"
            ],
            "data_quality_errors_tags": [
              
            ],
            "ingredients_text_debug": "CARBONATED WATER, HIGH FRUCTOSE CORN SYRUP, SUCROSE, CARAMEL COLOUR, PHOSPHORIC ACID, FLAVOURINGS, CAFFEINE",
            "max_imgid": "5",
            "misc_tags": [
              "en:nutrition-fruits-vegetables-nuts-estimate-from-ingredients",
              "en:nutrition-all-nutriscore-values-known",
              "en:nutriscore-computed"
            ]
          }
        }    
        """.trimIndent()
}