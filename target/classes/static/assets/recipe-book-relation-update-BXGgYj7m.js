import{d as A,i as p,r as u,B as L,C as U,u as I,f as D,c as H,_ as M,j as N,t as r,s as i,I as j,y as n,K as B,L as F,D as T,O as S,F as g,v as b,q as y,E as C,k as s}from"./app-9yHSs_0k.js";import{u as q}from"./index-CQAvtwPr.js";import{R as K}from"./recipe-book-relation.service-B02sJgJu.js";import{R as G}from"./recipe.service-oeltP7nx.js";import{R as J}from"./recipe-book.service-D-CAdHjl.js";class P{constructor(o,a,d){this.id=o,this.recipe=a,this.recipeBook=d}}const Q=A({compatConfig:{MODE:3},name:"RecipeBookRelationUpdate",setup(){var h;const e=p("recipeBookRelationService",()=>new K),o=p("alertService",()=>D(),!0),a=u(new P),d=p("recipeService",()=>new G),v=u([]),R=p("recipeBookService",()=>new J),l=u([]),t=u(!1),$=p("currentLanguage",()=>H(()=>navigator.language??"en"),!0),m=L(),w=U(),z=()=>w.go(-1),E=async c=>{try{const k=await e().find(c);a.value=k}catch(k){o.showHttpError(k.response)}};(h=m.params)!=null&&h.recipeBookRelationId&&E(m.params.recipeBookRelationId),(()=>{d().retrieve().then(c=>{v.value=c.data}),R().retrieve().then(c=>{l.value=c.data})})();const{t:V}=I(),f=q({recipe:{},recipeBook:{}},a);return f.value.$validate(),{recipeBookRelationService:e,alertService:o,recipeBookRelation:a,previousState:z,isSaving:t,currentLanguage:$,recipes:v,recipeBooks:l,v$:f,t$:V}},created(){},methods:{save(){this.isSaving=!0,this.recipeBookRelation.id?this.recipeBookRelationService().update(this.recipeBookRelation).then(e=>{this.isSaving=!1,this.previousState(),this.alertService.showInfo(this.t$("szakdolgozatApp.recipeBookRelation.updated",{param:e.id}))}).catch(e=>{this.isSaving=!1,this.alertService.showHttpError(e.response)}):this.recipeBookRelationService().create(this.recipeBookRelation).then(e=>{this.isSaving=!1,this.previousState(),this.alertService.showSuccess(this.t$("szakdolgozatApp.recipeBookRelation.created",{param:e.id}).toString())}).catch(e=>{this.isSaving=!1,this.alertService.showHttpError(e.response)})}}}),W={class:"row justify-content-center"},X={class:"col-8"},Y=["textContent"],Z={key:0,class:"form-group"},O=["textContent"],_={class:"form-group"},x=["textContent"],ee=["value"],oe={class:"form-group"},te=["textContent"],ie=["value"],ne=["textContent"],ae=["disabled"],re=["textContent"];function se(e,o,a,d,v,R){const l=N("font-awesome-icon");return s(),r("div",W,[i("div",X,[i("form",{name:"editForm",novalidate:"",onSubmit:o[4]||(o[4]=j(t=>e.save(),["prevent"]))},[i("h2",{id:"szakdolgozatApp.recipeBookRelation.home.createOrEditLabel","data-cy":"RecipeBookRelationCreateUpdateHeading",textContent:n(e.t$("szakdolgozatApp.recipeBookRelation.home.createOrEditLabel"))},null,8,Y),i("div",null,[e.recipeBookRelation.id?(s(),r("div",Z,[i("label",{for:"id",textContent:n(e.t$("global.field.id"))},null,8,O),B(i("input",{type:"text",class:"form-control",id:"id",name:"id","onUpdate:modelValue":o[0]||(o[0]=t=>e.recipeBookRelation.id=t),readonly:""},null,512),[[F,e.recipeBookRelation.id]])])):T("",!0),i("div",_,[i("label",{class:"form-control-label",textContent:n(e.t$("szakdolgozatApp.recipeBookRelation.recipe")),for:"recipe-book-relation-recipe"},null,8,x),B(i("select",{class:"form-control",id:"recipe-book-relation-recipe","data-cy":"recipe",name:"recipe","onUpdate:modelValue":o[1]||(o[1]=t=>e.recipeBookRelation.recipe=t)},[o[5]||(o[5]=i("option",{value:null},null,-1)),(s(!0),r(g,null,b(e.recipes,t=>(s(),r("option",{value:e.recipeBookRelation.recipe&&t.id===e.recipeBookRelation.recipe.id?e.recipeBookRelation.recipe:t,key:t.id},n(t.id),9,ee))),128))],512),[[S,e.recipeBookRelation.recipe]])]),i("div",oe,[i("label",{class:"form-control-label",textContent:n(e.t$("szakdolgozatApp.recipeBookRelation.recipeBook")),for:"recipe-book-relation-recipeBook"},null,8,te),B(i("select",{class:"form-control",id:"recipe-book-relation-recipeBook","data-cy":"recipeBook",name:"recipeBook","onUpdate:modelValue":o[2]||(o[2]=t=>e.recipeBookRelation.recipeBook=t)},[o[6]||(o[6]=i("option",{value:null},null,-1)),(s(!0),r(g,null,b(e.recipeBooks,t=>(s(),r("option",{value:e.recipeBookRelation.recipeBook&&t.id===e.recipeBookRelation.recipeBook.id?e.recipeBookRelation.recipeBook:t,key:t.id},n(t.id),9,ie))),128))],512),[[S,e.recipeBookRelation.recipeBook]])])]),i("div",null,[i("button",{type:"button",id:"cancel-save","data-cy":"entityCreateCancelButton",class:"btn btn-secondary",onClick:o[3]||(o[3]=t=>e.previousState())},[y(l,{icon:"ban"}),o[7]||(o[7]=C(" ")),i("span",{textContent:n(e.t$("entity.action.cancel"))},null,8,ne)]),i("button",{type:"submit",id:"save-entity","data-cy":"entityCreateSaveButton",disabled:e.v$.$invalid||e.isSaving,class:"btn btn-primary"},[y(l,{icon:"save"}),o[8]||(o[8]=C(" ")),i("span",{textContent:n(e.t$("entity.action.save"))},null,8,re)],8,ae)])],32)])])}const Be=M(Q,[["render",se]]);export{Be as default};
