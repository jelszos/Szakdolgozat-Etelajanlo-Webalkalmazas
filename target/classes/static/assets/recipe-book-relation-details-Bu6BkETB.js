import{d as v,i as f,B as y,C as h,r as w,u as $,f as S,_ as b,j as C,t as l,s as o,y as n,E as s,q as p,m as R,D as d,I as g,l as D,k as r}from"./app-9yHSs_0k.js";import{R as I}from"./recipe-book-relation.service-B02sJgJu.js";const z=v({compatConfig:{MODE:3},name:"RecipeBookRelationDetails",setup(){var a;const e=f("recipeBookRelationService",()=>new I),t=f("alertService",()=>S(),!0),k=y(),m=h(),_=()=>m.go(-1),u=w({}),i=async c=>{try{const B=await e().find(c);u.value=B}catch(B){t.showHttpError(B.response)}};return(a=k.params)!=null&&a.recipeBookRelationId&&i(k.params.recipeBookRelationId),{alertService:t,recipeBookRelation:u,previousState:_,t$:$().t}}}),V={class:"row justify-content-center"},j={class:"col-8"},E={key:0},A={class:"jh-entity-heading","data-cy":"recipeBookRelationDetailsHeading"},N=["textContent"],H={class:"row jh-entity-details"},M=["textContent"],q={key:0},O=["textContent"],T={key:0},F=["textContent"],G=["onClick"],J=["textContent"];function K(e,t,k,m,_,u){const i=C("router-link"),a=C("font-awesome-icon");return r(),l("div",V,[o("div",j,[e.recipeBookRelation?(r(),l("div",E,[o("h2",A,[o("span",{textContent:n(e.t$("szakdolgozatApp.recipeBookRelation.detail.title"))},null,8,N),s(" "+n(e.recipeBookRelation.id),1)]),o("dl",H,[o("dt",null,[o("span",{textContent:n(e.t$("szakdolgozatApp.recipeBookRelation.recipe"))},null,8,M)]),o("dd",null,[e.recipeBookRelation.recipe?(r(),l("div",q,[p(i,{to:{name:"RecipeView",params:{recipeId:e.recipeBookRelation.recipe.id}}},{default:R(()=>[s(n(e.recipeBookRelation.recipe.id),1)]),_:1},8,["to"])])):d("",!0)]),o("dt",null,[o("span",{textContent:n(e.t$("szakdolgozatApp.recipeBookRelation.recipeBook"))},null,8,O)]),o("dd",null,[e.recipeBookRelation.recipeBook?(r(),l("div",T,[p(i,{to:{name:"RecipeBookView",params:{recipeBookId:e.recipeBookRelation.recipeBook.id}}},{default:R(()=>[s(n(e.recipeBookRelation.recipeBook.id),1)]),_:1},8,["to"])])):d("",!0)])]),o("button",{type:"submit",onClick:t[0]||(t[0]=g(c=>e.previousState(),["prevent"])),class:"btn btn-info","data-cy":"entityDetailsBackButton"},[p(a,{icon:"arrow-left"}),t[1]||(t[1]=s(" ")),o("span",{textContent:n(e.t$("entity.action.back"))},null,8,F)]),e.recipeBookRelation.id?(r(),D(i,{key:0,to:{name:"RecipeBookRelationEdit",params:{recipeBookRelationId:e.recipeBookRelation.id}},custom:""},{default:R(({navigate:c})=>[o("button",{onClick:c,class:"btn btn-primary"},[p(a,{icon:"pencil-alt"}),t[2]||(t[2]=s(" ")),o("span",{textContent:n(e.t$("entity.action.edit"))},null,8,J)],8,G)]),_:1},8,["to"])):d("",!0)])):d("",!0)])])}const Q=b(z,[["render",K]]);export{Q as default};
