import{u as A}from"./app-9yHSs_0k.js";const S="https://www.jhipster.tech/problem",C=`${S}/email-already-used`,u=`${S}/login-already-used`,t="../../content/images/man_1.png",l="../../content/images/man_2.png",E="../../content/images/man_3.png",s="../../content/images/woman_1.png",p="../../content/images/woman_2.png",g="../../content/images/woman_3.png",N="../../content/images/food-type-appetizer.avif",I="../../content/images/food-type-soup.jpg",n="../../content/images/food-type-salad.avif",r="../../content/images/food-type-sauce.jpg",d="../../content/images/food-type-stew.avif",T="../../content/images/food-type-dessert.png",R="../../content/images/food-type-noodle.jpg",y={getAvatarImg:o=>{const e=[t,l,E,s,p,g];return e[o-1]||e[0]},getAllAvatarImgs(){return[t,l,E,s,p,g]},formatFoodCategory(o){return o?o.replace(/_/g," ").replace(/\b\w/g,e=>e.toUpperCase()):""},formatFoodCategoryToLabel:o=>o?o.replace(/_/g," ").replace(/\b\w/g,e=>e.toUpperCase()).replace(/\B\w/g,e=>e.toLowerCase()):"",getFoodType:o=>{const e={APPETIZER:N,SOUP:I,SALAD:n,SAUCE:r,STEW:d,DESSERT:T,NOODLE:R};if(typeof o=="number"){const a=Object.values(e);return a[o-1]||a[0]}else if(typeof o=="string"){const a=o.toUpperCase();return e[a]||null}return null},getFoodCategory:o=>{const e={CHINESE:"CHINESE",ITALIAN:"ITALIAN",HUNGARIAN:"HUNGARIAN",MEXICAN:"MEXICAN",INDIAN:"INDIAN",JAPANESE:"JAPANESE",THAI:"THAI",AMERICAN:"AMERICAN",EASTERN_EUROPEAN:"EASTERN_EUROPEAN",VIETNAMESE:"VIETNAMESE",RUSSIAN:"RUSSIAN",SPANISH:"SPANISH",FRENCH:"FRENCH",GREEK:"GREEK",MIDDLE_EASTERN:"MIDDLE_EASTERN",LATIN_AMERICAN:"LATIN_AMERICAN",BRITISH:"BRITISH",AUSTRALIAN:"AUSTRALIAN",UKRAINIAN:"UKRAINIAN",GERMAN:"GERMAN"};if(typeof o=="number"){const a=Object.values(e);return a[o-1]||a[0]}else if(typeof o=="string"){const a=o.toUpperCase();return e[a]||null}return null},getAllFoodCategories(){const{t:o}=A();return[{label:o("szakdolgozatApp.FoodCategory.CHINESE"),value:"CHINESE"},{label:o("szakdolgozatApp.FoodCategory.ITALIAN"),value:"ITALIAN"},{label:o("szakdolgozatApp.FoodCategory.HUNGARIAN"),value:"HUNGARIAN"},{label:o("szakdolgozatApp.FoodCategory.MEXICAN"),value:"MEXICAN"},{label:o("szakdolgozatApp.FoodCategory.INDIAN"),value:"INDIAN"},{label:o("szakdolgozatApp.FoodCategory.JAPANESE"),value:"JAPANESE"},{label:o("szakdolgozatApp.FoodCategory.THAI"),value:"THAI"},{label:o("szakdolgozatApp.FoodCategory.AMERICAN"),value:"AMERICAN"},{label:o("szakdolgozatApp.FoodCategory.EASTERN_EUROPEAN"),value:"EASTERN_EUROPEAN"},{label:o("szakdolgozatApp.FoodCategory.VIETNAMESE"),value:"VIETNAMESE"},{label:o("szakdolgozatApp.FoodCategory.RUSSIAN"),value:"RUSSIAN"},{label:o("szakdolgozatApp.FoodCategory.SPANISH"),value:"SPANISH"},{label:o("szakdolgozatApp.FoodCategory.FRENCH"),value:"FRENCH"},{label:o("szakdolgozatApp.FoodCategory.GREEK"),value:"GREEK"},{label:o("szakdolgozatApp.FoodCategory.MIDDLE_EASTERN"),value:"MIDDLE_EASTERN"},{label:o("szakdolgozatApp.FoodCategory.LATIN_AMERICAN"),value:"LATIN_AMERICAN"},{label:o("szakdolgozatApp.FoodCategory.BRITISH"),value:"BRITISH"},{label:o("szakdolgozatApp.FoodCategory.AUSTRALIAN"),value:"AUSTRALIAN"},{label:o("szakdolgozatApp.FoodCategory.UKRAINIAN"),value:"UKRAINIAN"},{label:o("szakdolgozatApp.FoodCategory.GERMAN"),value:"GERMAN"}]},getAllFoodTypes(){const{t:o}=A();return[{label:o("szakdolgozatApp.FoodType.APPETIZER"),image:N,value:"APPETIZER"},{label:o("szakdolgozatApp.FoodType.SOUP"),image:I,value:"SOUP"},{label:o("szakdolgozatApp.FoodType.SALAD"),image:n,value:"SALAD"},{label:o("szakdolgozatApp.FoodType.SAUCE"),image:r,value:"SAUCE"},{label:o("szakdolgozatApp.FoodType.STEW"),image:d,value:"STEW"},{label:o("szakdolgozatApp.FoodType.DESSERT"),image:T,value:"DESSERT"},{label:o("szakdolgozatApp.FoodType.NOODLE"),image:R,value:"NOODLE"}]}};export{C as E,u as L,y as f};
