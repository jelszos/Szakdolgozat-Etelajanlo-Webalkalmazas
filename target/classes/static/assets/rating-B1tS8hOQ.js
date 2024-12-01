import{d as w,u as D,i as z,r as _,o as R,f as A,_ as S,j as y,R as x,t as u,s as t,y as n,q as a,m as d,D as k,F as E,v as B,k as p,E as $,K as F,l as I}from"./app-9yHSs_0k.js";import{R as j}from"./rating.service-IMA_Zjbv.js";import{u as L}from"./date-format-GlMVmkoP.js";const V=w({compatConfig:{MODE:3},name:"Rating",setup(){const{t:e}=D(),s=L(),C=z("ratingService",()=>new j),h=z("alertService",()=>A(),!0),v=_([]),g=_(!1),r=()=>{},i=async()=>{g.value=!0;try{const l=await C().retrieve();v.value=l.data}catch(l){h.showHttpError(l.response)}finally{g.value=!1}},b=()=>{i()};R(async()=>{await i()});const c=_(null),m=_(null),o=l=>{c.value=l.id,m.value.show()},f=()=>{m.value.hide()};return{ratings:v,handleSyncList:b,isFetching:g,retrieveRatings:i,clear:r,...s,removeId:c,removeEntity:m,prepareRemove:o,closeDialog:f,removeRating:async()=>{try{await C().delete(c.value);const l=e("szakdolgozatApp.rating.deleted",{param:c.value}).toString();h.showInfo(l,{variant:"danger"}),c.value=null,i(),f()}catch(l){h.showHttpError(l.response)}},t$:e}}}),H={id:"page-heading","data-cy":"RatingHeading"},N=["textContent"],q={class:"d-flex justify-content-end"},M=["disabled"],T=["textContent"],U=["onClick"],K=["textContent"],O={key:0,class:"alert alert-warning"},G=["textContent"],J={key:1,class:"table-responsive"},P={class:"table table-striped","aria-describedby":"ratings"},Q={scope:"row"},W=["textContent"],X={scope:"row"},Y=["textContent"],Z={scope:"row"},tt=["textContent"],et={scope:"row"},nt=["textContent"],ot={scope:"row"},st=["textContent"],at={scope:"row"},it=["textContent"],lt={scope:"row"},dt=["textContent"],rt={scope:"row"},ct=["textContent"],pt={key:0},ut={class:"text-right"},mt={class:"btn-group"},ht=["textContent"],gt=["textContent"],_t=["textContent"],yt=["textContent"],Ct={class:"modal-body"},vt=["textContent"],bt=["textContent"],ft=["textContent"];function kt(e,s,C,h,v,g){const r=y("font-awesome-icon"),i=y("router-link"),b=y("b-button"),c=y("b-modal"),m=x("b-modal");return p(),u("div",null,[t("h2",H,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.home.title")),id:"rating-heading"},null,8,N),t("div",q,[t("button",{class:"btn btn-info mr-2",onClick:s[0]||(s[0]=(...o)=>e.handleSyncList&&e.handleSyncList(...o)),disabled:e.isFetching},[a(r,{icon:"sync",spin:e.isFetching},null,8,["spin"]),t("span",{textContent:n(e.t$("szakdolgozatApp.rating.home.refreshListLabel"))},null,8,T)],8,M),a(i,{to:{name:"RatingCreate"},custom:""},{default:d(({navigate:o})=>[t("button",{onClick:o,id:"jh-create-entity","data-cy":"entityCreateButton",class:"btn btn-primary jh-create-entity create-rating"},[a(r,{icon:"plus"}),t("span",{textContent:n(e.t$("szakdolgozatApp.rating.home.createLabel"))},null,8,K)],8,U)]),_:1})])]),s[4]||(s[4]=t("br",null,null,-1)),!e.isFetching&&e.ratings&&e.ratings.length===0?(p(),u("div",O,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.home.notFound"))},null,8,G)])):k("",!0),e.ratings&&e.ratings.length>0?(p(),u("div",J,[t("table",P,[t("thead",null,[t("tr",null,[t("th",Q,[t("span",{textContent:n(e.t$("global.field.id"))},null,8,W)]),t("th",X,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.rate"))},null,8,Y)]),t("th",Z,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.title"))},null,8,tt)]),t("th",et,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.description"))},null,8,nt)]),t("th",ot,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.imageUrl"))},null,8,st)]),t("th",at,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.createdDate"))},null,8,it)]),t("th",lt,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.user"))},null,8,dt)]),t("th",rt,[t("span",{textContent:n(e.t$("szakdolgozatApp.rating.recipe"))},null,8,ct)]),s[3]||(s[3]=t("th",{scope:"row"},null,-1))])]),t("tbody",null,[(p(!0),u(E,null,B(e.ratings,o=>(p(),u("tr",{key:o.id,"data-cy":"entityTable"},[t("td",null,[a(i,{to:{name:"RatingView",params:{ratingId:o.id}}},{default:d(()=>[$(n(o.id),1)]),_:2},1032,["to"])]),t("td",null,n(o.rate),1),t("td",null,n(o.title),1),t("td",null,n(o.description),1),t("td",null,n(o.imageUrl),1),t("td",null,n(e.formatDateShort(o.createdDate)||""),1),t("td",null,n(o.user?o.user.id:""),1),t("td",null,[o.recipe?(p(),u("div",pt,[a(i,{to:{name:"RecipeView",params:{recipeId:o.recipe.id}}},{default:d(()=>[$(n(o.recipe.id),1)]),_:2},1032,["to"])])):k("",!0)]),t("td",ut,[t("div",mt,[a(i,{to:{name:"RatingView",params:{ratingId:o.id}},class:"btn btn-info btn-sm details","data-cy":"entityDetailsButton"},{default:d(()=>[a(r,{icon:"eye"}),t("span",{class:"d-none d-md-inline",textContent:n(e.t$("entity.action.view"))},null,8,ht)]),_:2},1032,["to"]),a(i,{to:{name:"RatingEdit",params:{ratingId:o.id}},class:"btn btn-primary btn-sm edit","data-cy":"entityEditButton"},{default:d(()=>[a(r,{icon:"pencil-alt"}),t("span",{class:"d-none d-md-inline",textContent:n(e.t$("entity.action.edit"))},null,8,gt)]),_:2},1032,["to"]),F((p(),I(b,{onClick:f=>e.prepareRemove(o),variant:"danger",class:"btn btn-sm","data-cy":"entityDeleteButton"},{default:d(()=>[a(r,{icon:"times"}),t("span",{class:"d-none d-md-inline",textContent:n(e.t$("entity.action.delete"))},null,8,_t)]),_:2},1032,["onClick"])),[[m,void 0,void 0,{removeEntity:!0}]])])])]))),128))])])])):k("",!0),a(c,{ref:"removeEntity",id:"removeEntity"},{"modal-title":d(()=>[t("span",{id:"szakdolgozatApp.rating.delete.question","data-cy":"ratingDeleteDialogHeading",textContent:n(e.t$("entity.delete.title"))},null,8,yt)]),"modal-footer":d(()=>[t("div",null,[t("button",{type:"button",class:"btn btn-secondary",textContent:n(e.t$("entity.action.cancel")),onClick:s[1]||(s[1]=o=>e.closeDialog())},null,8,bt),t("button",{type:"button",class:"btn btn-primary",id:"jhi-confirm-delete-rating","data-cy":"entityConfirmDeleteButton",textContent:n(e.t$("entity.action.delete")),onClick:s[2]||(s[2]=o=>e.removeRating())},null,8,ft)])]),default:d(()=>[t("div",Ct,[t("p",{id:"jhi-delete-rating-heading",textContent:n(e.t$("szakdolgozatApp.rating.delete.question",{id:e.removeId}))},null,8,vt)])]),_:1},512)])}const Rt=S(V,[["render",kt]]);export{Rt as default};
