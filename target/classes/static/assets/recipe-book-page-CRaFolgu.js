import{d as V,g as I,i as _,B as A,C as P,r as w,w as D,o as C,u as R,f as S,_ as q,j as i,t as y,s as n,q as a,y as f,m as l,I as E,F as L,v as j,k as B,E as z,l as U}from"./app-9yHSs_0k.js";import{R as M}from"./recipe-book.service-D-CAdHjl.js";import{u as N}from"./date-format-GlMVmkoP.js";import{R as G}from"./RecipeBookCard-Y34C4UwL.js";import{R as H}from"./recipe-book-relation.service-B02sJgJu.js";import{h as O}from"./header-bg-C45d2Xww.js";import"./functions-BhiSqBls.js";const T=V({compatConfig:{MODE:3},name:"RecipeBookPage",components:{FontAwesomeIcon:I,RecipeBookCard:G},setup(){const e=N(),o=_("recipeBookService",()=>new M);_("recipeRelationService",()=>new H);const k=_("alertService",()=>S(),!0),c=A(),m=P(),r={title:"",description:"",page:c.query.page||0,tag:"",theme:""},p=w({totalElements:0,currentPage:parseInt(c.query.page)-1,totalPages:0});D(()=>c.query.page,t=>{console.log(c.query),p.value.currentPage=t?parseInt(t):0,r.page=t?parseInt(t):0,g(),m.push({query:{page:r.page,title:r.title,description:r.description}})});const v=()=>m.go(-1),h=w([]),g=async()=>{try{const t=await o().retrieve(r);console.log(t),h.value=t.data.content,p.value={...p,currentPage:t.data.number,totalElements:t.data.totalElements,totalPages:t.data.totalPages},await m.push({query:{page:r.page,title:r.title,description:r.description}})}catch(t){k.showHttpError(t.response)}};function d(){return"#"}C(async()=>{await g()});function u(t){const b=parseInt(t.target.text)-1;m.push({query:{...c.query,page:b}}),p.value.currentPage=b}return{...e,alertService:k,recipeBooks:h,searchFormData:r,paginationInfo:p,headerBg:O,pLinkGen:d,onPageChange:u,search:g,previousState:v,t$:R().t}},methods:{}}),J={class:"container"},K={class:"d-flex justify-content-center flex-column"},Q={class:"position-relative text-center my-5"},W=["textContent"],X={class:"mt-3"},Y={class:"icon-text"},Z={class:"text-slate-900",style:{"font-size":"22px","font-weight":"700"}},x={class:"mt-4 d-flex align-items-end custom-gap"},ee={class:"mb-3"},oe={class:"d-flex text-slate-900 align-items-center justify-content-between",style:{"font-size":"20px","font-weight":"500"}},te={class:"recipe-books"};function ae(e,o,k,c,m,r){const p=i("b-img"),v=i("b-badge"),h=i("font-awesome-icon"),g=i("b-alert"),d=i("b-form-input"),u=i("b-input-group"),t=i("b-form-group"),b=i("b-button"),F=i("recipe-book-card"),$=i("b-pagination-nav");return B(),y("div",J,[n("div",K,[n("div",Q,[a(p,{src:e.headerBg,class:"carousel-header-img"},null,8,["src"]),n("h1",{class:"carousel-header mb-0 position-relative",textContent:f(e.t$("szakdolgozatApp.recipeBook.home.title"))},null,8,W)]),a(v,{class:"ml-3"},{default:l(()=>o[5]||(o[5]=[z("Public recipe books")])),_:1}),a(g,{show:""},{default:l(()=>[a(h,{icon:"info-circle"}),z(" "+f(e.t$("szakdolgozatApp.recipeBook.home.subtitle")),1)]),_:1}),n("div",X,[n("div",Y,[n("p",Z,f(e.t$("szakdolgozatApp.recipeBook.search.info")),1)]),n("div",x,[a(t,{label:e.t$("szakdolgozatApp.recipeBook.search.labelField"),class:"flex-lg-grow-1 w-100"},{default:l(()=>[a(u,null,{default:l(()=>[a(d,{placeholder:e.t$("szakdolgozatApp.recipeBook.search.titleField"),modelValue:e.searchFormData.title,"onUpdate:modelValue":o[0]||(o[0]=s=>e.searchFormData.title=s)},null,8,["placeholder","modelValue"])]),_:1})]),_:1},8,["label"]),a(t,{label:e.t$("szakdolgozatApp.recipeBook.search.descriptionLabel"),class:"flex-lg-grow-1 w-100"},{default:l(()=>[a(u,null,{default:l(()=>[a(d,{placeholder:e.t$("szakdolgozatApp.recipeBook.search.descriptionField"),modelValue:e.searchFormData.description,"onUpdate:modelValue":o[1]||(o[1]=s=>e.searchFormData.description=s)},null,8,["placeholder","modelValue"])]),_:1})]),_:1},8,["label"]),a(t,{label:e.t$("szakdolgozatApp.recipeBook.search.themeLabel"),class:"flex-lg-grow-1 w-100"},{default:l(()=>[a(u,null,{default:l(()=>[a(d,{placeholder:e.t$("szakdolgozatApp.recipeBook.search.themeField"),modelValue:e.searchFormData.theme,"onUpdate:modelValue":o[2]||(o[2]=s=>e.searchFormData.theme=s)},null,8,["placeholder","modelValue"])]),_:1})]),_:1},8,["label"]),a(t,{label:e.t$("szakdolgozatApp.recipeBook.search.tagLabel"),class:"flex-lg-grow-1 w-100"},{default:l(()=>[a(u,null,{default:l(()=>[a(d,{placeholder:e.t$("szakdolgozatApp.recipeBook.search.tagField"),modelValue:e.searchFormData.tag,"onUpdate:modelValue":o[3]||(o[3]=s=>e.searchFormData.tag=s)},null,8,["placeholder","modelValue"])]),_:1})]),_:1},8,["label"]),n("div",ee,[a(b,{variant:"primary",onClick:o[4]||(o[4]=E(s=>e.search(),["prevent","stop"]))},{default:l(()=>o[6]||(o[6]=[z("Search")])),_:1})])])]),o[8]||(o[8]=n("div",{class:"w-100 divider my-5"},null,-1)),n("div",oe,[n("p",null,f(e.paginationInfo.currentPage+1)+". page",1),n("p",null,f(e.paginationInfo.totalElements)+" recipe book(s)",1)]),n("div",te,[(B(!0),y(L,null,j(e.recipeBooks,s=>(B(),U(F,{key:s.id,"recipe-book":s},null,8,["recipe-book"]))),128)),o[7]||(o[7]=n("br",null,null,-1))])]),a($,{"number-of-pages":e.paginationInfo.totalPages,page:e.paginationInfo.currentPage,"link-gen":e.pLinkGen,onclick:e.onPageChange,class:"mt-5",value:parseInt(e.paginationInfo.currentPage)+1,"hide-goto-end-buttons":""},null,8,["number-of-pages","page","link-gen","onclick","value"])])}const de=q(T,[["render",ae]]);export{de as default};