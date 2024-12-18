import{d as z,u as $,i as B,r as _,o as w,f as D,_ as R,j as y,R as A,t as m,s as t,y as o,q as i,m as d,D as f,F as S,v as E,k as p,E as x,K as F,l as j}from"./app-DpqgLFQi.js";import{R as I}from"./recipe-book.service-Df3-flx1.js";import{u as L}from"./date-format-rfZit4qg.js";const V=z({compatConfig:{MODE:3},name:"RecipeBook",setup(){const{t:e}=$(),s=L(),C=B("recipeBookService",()=>new I),h=B("alertService",()=>D(),!0),v=_([]),k=_(!1),r=()=>{},a=async()=>{k.value=!0;try{const l=await C().retrieve();v.value=l.data}catch(l){h.showHttpError(l.response)}finally{k.value=!1}},b=()=>{a()};w(async()=>{await a()});const c=_(null),u=_(null),n=l=>{c.value=l.id,u.value.show()},g=()=>{u.value.hide()};return{recipeBooks:v,handleSyncList:b,isFetching:k,retrieveRecipeBooks:a,clear:r,...s,removeId:c,removeEntity:u,prepareRemove:n,closeDialog:g,removeRecipeBook:async()=>{try{await C().delete(c.value);const l=e("szakdolgozatApp.recipeBook.deleted",{param:c.value}).toString();h.showInfo(l,{variant:"danger"}),c.value=null,a(),g()}catch(l){h.showHttpError(l.response)}},t$:e}}}),H={id:"page-heading","data-cy":"RecipeBookHeading"},N=["textContent"],q={class:"d-flex justify-content-end"},M=["disabled"],T=["textContent"],K=["onClick"],O=["textContent"],G={key:0,class:"alert alert-warning"},J=["textContent"],P={key:1,class:"table-responsive"},Q={class:"table table-striped","aria-describedby":"recipeBooks"},U={scope:"row"},W=["textContent"],X={scope:"row"},Y=["textContent"],Z={scope:"row"},tt=["textContent"],et={scope:"row"},ot=["textContent"],nt={scope:"row"},st=["textContent"],it={scope:"row"},at=["textContent"],lt={scope:"row"},dt=["textContent"],rt={class:"text-right"},ct={class:"btn-group"},pt=["textContent"],ut=["textContent"],mt=["textContent"],ht=["textContent"],kt={class:"modal-body"},_t=["textContent"],yt=["textContent"],Ct=["textContent"];function vt(e,s,C,h,v,k){const r=y("font-awesome-icon"),a=y("router-link"),b=y("b-button"),c=y("b-modal"),u=A("b-modal");return p(),m("div",null,[t("h2",H,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.home.title")),id:"recipe-book-heading"},null,8,N),t("div",q,[t("button",{class:"btn btn-info mr-2",onClick:s[0]||(s[0]=(...n)=>e.handleSyncList&&e.handleSyncList(...n)),disabled:e.isFetching},[i(r,{icon:"sync",spin:e.isFetching},null,8,["spin"]),t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.home.refreshListLabel"))},null,8,T)],8,M),i(a,{to:{name:"RecipeBookCreate"},custom:""},{default:d(({navigate:n})=>[t("button",{onClick:n,id:"jh-create-entity","data-cy":"entityCreateButton",class:"btn btn-primary jh-create-entity create-recipe-book"},[i(r,{icon:"plus"}),t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.home.createLabel"))},null,8,O)],8,K)]),_:1})])]),s[4]||(s[4]=t("br",null,null,-1)),!e.isFetching&&e.recipeBooks&&e.recipeBooks.length===0?(p(),m("div",G,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.home.notFound"))},null,8,J)])):f("",!0),e.recipeBooks&&e.recipeBooks.length>0?(p(),m("div",P,[t("table",Q,[t("thead",null,[t("tr",null,[t("th",U,[t("span",{textContent:o(e.t$("global.field.id"))},null,8,W)]),t("th",X,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.title"))},null,8,Y)]),t("th",Z,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.theme"))},null,8,tt)]),t("th",et,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.description"))},null,8,ot)]),t("th",nt,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.tags"))},null,8,st)]),t("th",it,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.createdDate"))},null,8,at)]),t("th",lt,[t("span",{textContent:o(e.t$("szakdolgozatApp.recipeBook.user"))},null,8,dt)]),s[3]||(s[3]=t("th",{scope:"row"},null,-1))])]),t("tbody",null,[(p(!0),m(S,null,E(e.recipeBooks,n=>(p(),m("tr",{key:n.id,"data-cy":"entityTable"},[t("td",null,[i(a,{to:{name:"RecipeBookView",params:{recipeBookId:n.id}}},{default:d(()=>[x(o(n.id),1)]),_:2},1032,["to"])]),t("td",null,o(n.title),1),t("td",null,o(n.theme),1),t("td",null,o(n.description),1),t("td",null,o(n.tags),1),t("td",null,o(e.formatDateShort(n.createdDate)||""),1),t("td",null,o(n.user?n.user.id:""),1),t("td",rt,[t("div",ct,[i(a,{to:{name:"RecipeBookView",params:{recipeBookId:n.id}},class:"btn btn-info btn-sm details","data-cy":"entityDetailsButton"},{default:d(()=>[i(r,{icon:"eye"}),t("span",{class:"d-none d-md-inline",textContent:o(e.t$("entity.action.view"))},null,8,pt)]),_:2},1032,["to"]),i(a,{to:{name:"RecipeBookEdit",params:{recipeBookId:n.id}},class:"btn btn-primary btn-sm edit","data-cy":"entityEditButton"},{default:d(()=>[i(r,{icon:"pencil-alt"}),t("span",{class:"d-none d-md-inline",textContent:o(e.t$("entity.action.edit"))},null,8,ut)]),_:2},1032,["to"]),F((p(),j(b,{onClick:g=>e.prepareRemove(n),variant:"danger",class:"btn btn-sm","data-cy":"entityDeleteButton"},{default:d(()=>[i(r,{icon:"times"}),t("span",{class:"d-none d-md-inline",textContent:o(e.t$("entity.action.delete"))},null,8,mt)]),_:2},1032,["onClick"])),[[u,void 0,void 0,{removeEntity:!0}]])])])]))),128))])])])):f("",!0),i(c,{ref:"removeEntity",id:"removeEntity"},{"modal-title":d(()=>[t("span",{id:"szakdolgozatApp.recipeBook.delete.question","data-cy":"recipeBookDeleteDialogHeading",textContent:o(e.t$("entity.delete.title"))},null,8,ht)]),"modal-footer":d(()=>[t("div",null,[t("button",{type:"button",class:"btn btn-secondary",textContent:o(e.t$("entity.action.cancel")),onClick:s[1]||(s[1]=n=>e.closeDialog())},null,8,yt),t("button",{type:"button",class:"btn btn-primary",id:"jhi-confirm-delete-recipeBook","data-cy":"entityConfirmDeleteButton",textContent:o(e.t$("entity.action.delete")),onClick:s[2]||(s[2]=n=>e.removeRecipeBook())},null,8,Ct)])]),default:d(()=>[t("div",kt,[t("p",{id:"jhi-delete-recipeBook-heading",textContent:o(e.t$("szakdolgozatApp.recipeBook.delete.question",{id:e.removeId}))},null,8,_t)])]),_:1},512)])}const zt=R(V,[["render",vt]]);export{zt as default};
