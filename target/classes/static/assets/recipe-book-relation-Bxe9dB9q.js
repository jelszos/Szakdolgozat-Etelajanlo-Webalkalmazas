import{d as w,u as $,i as f,r as y,o as z,f as D,_ as E,j as _,R as S,t as u,s as e,y as n,q as s,m as l,D as v,F as A,v as I,k as c,E as R,K as V,l as j}from"./app-9yHSs_0k.js";import{R as x}from"./recipe-book-relation.service-B02sJgJu.js";const F=w({compatConfig:{MODE:3},name:"RecipeBookRelation",setup(){const{t}=$(),i=f("recipeBookRelationService",()=>new x),k=f("alertService",()=>D(),!0),b=y([]),h=y(!1),B=()=>{},a=async()=>{h.value=!0;try{const d=await i().retrieve();b.value=d.data}catch(d){k.showHttpError(d.response)}finally{h.value=!1}},r=()=>{a()};z(async()=>{await a()});const p=y(null),m=y(null),C=d=>{p.value=d.id,m.value.show()},o=()=>{m.value.hide()};return{recipeBookRelations:b,handleSyncList:r,isFetching:h,retrieveRecipeBookRelations:a,clear:B,removeId:p,removeEntity:m,prepareRemove:C,closeDialog:o,removeRecipeBookRelation:async()=>{try{await i().delete(p.value);const d=t("szakdolgozatApp.recipeBookRelation.deleted",{param:p.value}).toString();k.showInfo(d,{variant:"danger"}),p.value=null,a(),o()}catch(d){k.showHttpError(d.response)}},t$:t}}}),L={id:"page-heading","data-cy":"RecipeBookRelationHeading"},H=["textContent"],N={class:"d-flex justify-content-end"},q=["disabled"],M=["textContent"],T=["onClick"],K=["textContent"],O={key:0,class:"alert alert-warning"},G=["textContent"],J={key:1,class:"table-responsive"},P={class:"table table-striped","aria-describedby":"recipeBookRelations"},Q={scope:"row"},U=["textContent"],W={scope:"row"},X=["textContent"],Y={scope:"row"},Z=["textContent"],ee={key:0},te={key:0},oe={class:"text-right"},ne={class:"btn-group"},ie=["textContent"],se=["textContent"],ae=["textContent"],le=["textContent"],de={class:"modal-body"},re=["textContent"],ce=["textContent"],pe=["textContent"];function ue(t,i,k,b,h,B){const a=_("font-awesome-icon"),r=_("router-link"),p=_("b-button"),m=_("b-modal"),C=S("b-modal");return c(),u("div",null,[e("h2",L,[e("span",{textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.home.title")),id:"recipe-book-relation-heading"},null,8,H),e("div",N,[e("button",{class:"btn btn-info mr-2",onClick:i[0]||(i[0]=(...o)=>t.handleSyncList&&t.handleSyncList(...o)),disabled:t.isFetching},[s(a,{icon:"sync",spin:t.isFetching},null,8,["spin"]),e("span",{textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.home.refreshListLabel"))},null,8,M)],8,q),s(r,{to:{name:"RecipeBookRelationCreate"},custom:""},{default:l(({navigate:o})=>[e("button",{onClick:o,id:"jh-create-entity","data-cy":"entityCreateButton",class:"btn btn-primary jh-create-entity create-recipe-book-relation"},[s(a,{icon:"plus"}),e("span",{textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.home.createLabel"))},null,8,K)],8,T)]),_:1})])]),i[4]||(i[4]=e("br",null,null,-1)),!t.isFetching&&t.recipeBookRelations&&t.recipeBookRelations.length===0?(c(),u("div",O,[e("span",{textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.home.notFound"))},null,8,G)])):v("",!0),t.recipeBookRelations&&t.recipeBookRelations.length>0?(c(),u("div",J,[e("table",P,[e("thead",null,[e("tr",null,[e("th",Q,[e("span",{textContent:n(t.t$("global.field.id"))},null,8,U)]),e("th",W,[e("span",{textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.recipe"))},null,8,X)]),e("th",Y,[e("span",{textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.recipeBook"))},null,8,Z)]),i[3]||(i[3]=e("th",{scope:"row"},null,-1))])]),e("tbody",null,[(c(!0),u(A,null,I(t.recipeBookRelations,o=>(c(),u("tr",{key:o.id,"data-cy":"entityTable"},[e("td",null,[s(r,{to:{name:"RecipeBookRelationView",params:{recipeBookRelationId:o.id}}},{default:l(()=>[R(n(o.id),1)]),_:2},1032,["to"])]),e("td",null,[o.recipe?(c(),u("div",ee,[s(r,{to:{name:"RecipeView",params:{recipeId:o.recipe.id}}},{default:l(()=>[R(n(o.recipe.id),1)]),_:2},1032,["to"])])):v("",!0)]),e("td",null,[o.recipeBook?(c(),u("div",te,[s(r,{to:{name:"RecipeBookView",params:{recipeBookId:o.recipeBook.id}}},{default:l(()=>[R(n(o.recipeBook.id),1)]),_:2},1032,["to"])])):v("",!0)]),e("td",oe,[e("div",ne,[s(r,{to:{name:"RecipeBookRelationView",params:{recipeBookRelationId:o.id}},class:"btn btn-info btn-sm details","data-cy":"entityDetailsButton"},{default:l(()=>[s(a,{icon:"eye"}),e("span",{class:"d-none d-md-inline",textContent:n(t.t$("entity.action.view"))},null,8,ie)]),_:2},1032,["to"]),s(r,{to:{name:"RecipeBookRelationEdit",params:{recipeBookRelationId:o.id}},class:"btn btn-primary btn-sm edit","data-cy":"entityEditButton"},{default:l(()=>[s(a,{icon:"pencil-alt"}),e("span",{class:"d-none d-md-inline",textContent:n(t.t$("entity.action.edit"))},null,8,se)]),_:2},1032,["to"]),V((c(),j(p,{onClick:g=>t.prepareRemove(o),variant:"danger",class:"btn btn-sm","data-cy":"entityDeleteButton"},{default:l(()=>[s(a,{icon:"times"}),e("span",{class:"d-none d-md-inline",textContent:n(t.t$("entity.action.delete"))},null,8,ae)]),_:2},1032,["onClick"])),[[C,void 0,void 0,{removeEntity:!0}]])])])]))),128))])])])):v("",!0),s(m,{ref:"removeEntity",id:"removeEntity"},{"modal-title":l(()=>[e("span",{id:"szakdolgozatApp.recipeBookRelation.delete.question","data-cy":"recipeBookRelationDeleteDialogHeading",textContent:n(t.t$("entity.delete.title"))},null,8,le)]),"modal-footer":l(()=>[e("div",null,[e("button",{type:"button",class:"btn btn-secondary",textContent:n(t.t$("entity.action.cancel")),onClick:i[1]||(i[1]=o=>t.closeDialog())},null,8,ce),e("button",{type:"button",class:"btn btn-primary",id:"jhi-confirm-delete-recipeBookRelation","data-cy":"entityConfirmDeleteButton",textContent:n(t.t$("entity.action.delete")),onClick:i[2]||(i[2]=o=>t.removeRecipeBookRelation())},null,8,pe)])]),default:l(()=>[e("div",de,[e("p",{id:"jhi-delete-recipeBookRelation-heading",textContent:n(t.t$("szakdolgozatApp.recipeBookRelation.delete.question",{id:t.removeId}))},null,8,re)])]),_:1},512)])}const he=E(F,[["render",ue]]);export{he as default};