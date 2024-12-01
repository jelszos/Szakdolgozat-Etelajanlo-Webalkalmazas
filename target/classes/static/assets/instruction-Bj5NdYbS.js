import{d as z,u as I,i as k,r as y,o as A,f as D,_ as E,j as v,R as S,t as p,s as t,y as o,q as i,m as r,D as g,F as x,v as B,k as u,E as $,K as j,l as F}from"./app-9yHSs_0k.js";import{I as L}from"./instruction.service-CKgwal-l.js";const V=z({compatConfig:{MODE:3},name:"Instruction",setup(){const{t:e}=I(),s=k("instructionService",()=>new L),h=k("alertService",()=>D(),!0),C=y([]),_=y(!1),f=()=>{},a=async()=>{_.value=!0;try{const l=await s().retrieve();C.value=l.data}catch(l){h.showHttpError(l.response)}finally{_.value=!1}},d=()=>{a()};A(async()=>{await a()});const c=y(null),m=y(null),b=l=>{c.value=l.id,m.value.show()},n=()=>{m.value.hide()};return{instructions:C,handleSyncList:d,isFetching:_,retrieveInstructions:a,clear:f,removeId:c,removeEntity:m,prepareRemove:b,closeDialog:n,removeInstruction:async()=>{try{await s().delete(c.value);const l=e("szakdolgozatApp.instruction.deleted",{param:c.value}).toString();h.showInfo(l,{variant:"danger"}),c.value=null,a(),n()}catch(l){h.showHttpError(l.response)}},t$:e}}}),q={id:"page-heading","data-cy":"InstructionHeading"},H=["textContent"],N={class:"d-flex justify-content-end"},R=["disabled"],T=["textContent"],M=["onClick"],K=["textContent"],O={key:0,class:"alert alert-warning"},G=["textContent"],J={key:1,class:"table-responsive"},P={class:"table table-striped","aria-describedby":"instructions"},Q={scope:"row"},U=["textContent"],W={scope:"row"},X=["textContent"],Y={scope:"row"},Z=["textContent"],tt={scope:"row"},et=["textContent"],nt={scope:"row"},ot=["textContent"],st={key:0},it={class:"text-right"},at={class:"btn-group"},lt=["textContent"],rt=["textContent"],dt=["textContent"],ct=["textContent"],ut={class:"modal-body"},pt=["textContent"],mt=["textContent"],ht=["textContent"];function _t(e,s,h,C,_,f){const a=v("font-awesome-icon"),d=v("router-link"),c=v("b-button"),m=v("b-modal"),b=S("b-modal");return u(),p("div",null,[t("h2",q,[t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.home.title")),id:"instruction-heading"},null,8,H),t("div",N,[t("button",{class:"btn btn-info mr-2",onClick:s[0]||(s[0]=(...n)=>e.handleSyncList&&e.handleSyncList(...n)),disabled:e.isFetching},[i(a,{icon:"sync",spin:e.isFetching},null,8,["spin"]),t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.home.refreshListLabel"))},null,8,T)],8,R),i(d,{to:{name:"InstructionCreate"},custom:""},{default:r(({navigate:n})=>[t("button",{onClick:n,id:"jh-create-entity","data-cy":"entityCreateButton",class:"btn btn-primary jh-create-entity create-instruction"},[i(a,{icon:"plus"}),t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.home.createLabel"))},null,8,K)],8,M)]),_:1})])]),s[4]||(s[4]=t("br",null,null,-1)),!e.isFetching&&e.instructions&&e.instructions.length===0?(u(),p("div",O,[t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.home.notFound"))},null,8,G)])):g("",!0),e.instructions&&e.instructions.length>0?(u(),p("div",J,[t("table",P,[t("thead",null,[t("tr",null,[t("th",Q,[t("span",{textContent:o(e.t$("global.field.id"))},null,8,U)]),t("th",W,[t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.title"))},null,8,X)]),t("th",Y,[t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.requiredTime"))},null,8,Z)]),t("th",tt,[t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.description"))},null,8,et)]),t("th",nt,[t("span",{textContent:o(e.t$("szakdolgozatApp.instruction.recipe"))},null,8,ot)]),s[3]||(s[3]=t("th",{scope:"row"},null,-1))])]),t("tbody",null,[(u(!0),p(x,null,B(e.instructions,n=>(u(),p("tr",{key:n.id,"data-cy":"entityTable"},[t("td",null,[i(d,{to:{name:"InstructionView",params:{instructionId:n.id}}},{default:r(()=>[$(o(n.id),1)]),_:2},1032,["to"])]),t("td",null,o(n.title),1),t("td",null,o(n.requiredTime),1),t("td",null,o(n.description),1),t("td",null,[n.recipe?(u(),p("div",st,[i(d,{to:{name:"RecipeView",params:{recipeId:n.recipe.id}}},{default:r(()=>[$(o(n.recipe.id),1)]),_:2},1032,["to"])])):g("",!0)]),t("td",it,[t("div",at,[i(d,{to:{name:"InstructionView",params:{instructionId:n.id}},class:"btn btn-info btn-sm details","data-cy":"entityDetailsButton"},{default:r(()=>[i(a,{icon:"eye"}),t("span",{class:"d-none d-md-inline",textContent:o(e.t$("entity.action.view"))},null,8,lt)]),_:2},1032,["to"]),i(d,{to:{name:"InstructionEdit",params:{instructionId:n.id}},class:"btn btn-primary btn-sm edit","data-cy":"entityEditButton"},{default:r(()=>[i(a,{icon:"pencil-alt"}),t("span",{class:"d-none d-md-inline",textContent:o(e.t$("entity.action.edit"))},null,8,rt)]),_:2},1032,["to"]),j((u(),F(c,{onClick:w=>e.prepareRemove(n),variant:"danger",class:"btn btn-sm","data-cy":"entityDeleteButton"},{default:r(()=>[i(a,{icon:"times"}),t("span",{class:"d-none d-md-inline",textContent:o(e.t$("entity.action.delete"))},null,8,dt)]),_:2},1032,["onClick"])),[[b,void 0,void 0,{removeEntity:!0}]])])])]))),128))])])])):g("",!0),i(m,{ref:"removeEntity",id:"removeEntity"},{"modal-title":r(()=>[t("span",{id:"szakdolgozatApp.instruction.delete.question","data-cy":"instructionDeleteDialogHeading",textContent:o(e.t$("entity.delete.title"))},null,8,ct)]),"modal-footer":r(()=>[t("div",null,[t("button",{type:"button",class:"btn btn-secondary",textContent:o(e.t$("entity.action.cancel")),onClick:s[1]||(s[1]=n=>e.closeDialog())},null,8,mt),t("button",{type:"button",class:"btn btn-primary",id:"jhi-confirm-delete-instruction","data-cy":"entityConfirmDeleteButton",textContent:o(e.t$("entity.action.delete")),onClick:s[2]||(s[2]=n=>e.removeInstruction())},null,8,ht)])]),default:r(()=>[t("div",ut,[t("p",{id:"jhi-delete-instruction-heading",textContent:o(e.t$("szakdolgozatApp.instruction.delete.question",{id:e.removeId}))},null,8,pt)])]),_:1},512)])}const Ct=E(V,[["render",_t]]);export{Ct as default};