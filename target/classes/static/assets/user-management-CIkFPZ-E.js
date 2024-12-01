import{d as I,i as $,r as a,u as _,f as L,_ as j,j as h,t as d,s as t,y as s,q as r,m as c,F as D,v as U,D as g,K as A,P as V,k as u,E as S}from"./app-9yHSs_0k.js";import{U as B}from"./user-management.service-Bd6QAZvG.js";import{u as z}from"./date-format-GlMVmkoP.js";import"./sorts-x2GDQqJR.js";const E=I({compatConfig:{MODE:3},name:"JhiUserManagementComponent",setup(e){const o=$("alertService",()=>L(),!0),{formatDateShort:y}=z(),k=$("userManagementService",()=>new B,!0),M=$("currentUsername"),O=a(""),m=a(""),p=a(20),l=a(1),v=a(1),C=a("id"),f=a(!1),b=a(!1),n=a(null),i=a([]),w=a(0),P=a(null);return{formatDate:y,userManagementService:k,alertService:o,error:O,success:m,itemsPerPage:p,page:l,previousPage:v,propOrder:C,reverse:f,isLoading:b,removeId:n,users:i,username:M,totalItems:w,queryCount:P,t$:_().t}},mounted(){this.loadAll()},methods:{setActive(e,o){e.activated=o,this.userManagementService.update(e).then(()=>{this.error=null,this.success="OK",this.loadAll()}).catch(()=>{this.success=null,this.error="ERROR",e.activated=!1})},loadAll(){this.isLoading=!0,this.userManagementService.retrieve({sort:this.sort(),page:this.page-1,size:this.itemsPerPage}).then(e=>{this.isLoading=!1,this.users=e.data,this.totalItems=Number(e.headers["x-total-count"]),this.queryCount=this.totalItems}).catch(()=>{this.isLoading=!1})},handleSyncList(){this.loadAll()},sort(){const e=[`${this.propOrder},${this.reverse?"desc":"asc"}`];return this.propOrder!=="id"&&e.push("id"),e},loadPage(e){e!==this.previousPage&&(this.previousPage=e,this.transition())},transition(){this.loadAll()},changeOrder(e){this.propOrder=e,this.reverse=!this.reverse,this.transition()},deleteUser(){this.userManagementService.remove(this.removeId).then(e=>{this.alertService.showInfo(this.t$(e.headers["x-szakdolgozatapp-alert"].toString(),{param:decodeURIComponent(e.headers["x-szakdolgozatapp-params"].replace(/\+/g," "))}),{variant:"danger"}),this.removeId=null,this.loadAll(),this.closeDialog()}).catch(e=>{this.alertService.showHttpError(e.response)})},prepareRemove(e){this.removeId=e.login,this.$refs.removeUser&&this.$refs.removeUser.show()},closeDialog(){this.$refs.removeUser&&this.$refs.removeUser.hide()}}}),K=["textContent"],R={class:"d-flex justify-content-end"},q=["disabled"],J=["textContent"],N=["onClick"],F=["textContent"],H={key:0,class:"table-responsive"},T={class:"table table-striped","aria-describedby":"Users"},G=["textContent"],Q=["textContent"],W=["textContent"],X=["textContent"],Y={scope:"col"},Z=["textContent"],x=["textContent"],ee=["textContent"],te=["textContent"],ne={key:0},oe=["id"],se={class:"jhi-user-email"},re=["onClick","textContent"],ie=["onClick","disabled","textContent"],ae={class:"badge badge-info"},le={class:"text-right"},de={class:"btn-group"},ue=["onClick"],me=["textContent"],ce=["onClick"],pe=["textContent"],he=["onClick"],ge=["textContent"],ve=["textContent"],Ce={class:"modal-body"},fe=["textContent"],be=["textContent"],$e=["textContent"],ye={class:"row justify-content-center"},ke={class:"row justify-content-center"};function Me(e,o,y,k,M,O){const m=h("font-awesome-icon"),p=h("router-link"),l=h("jhi-sort-indicator"),v=h("b-button"),C=h("b-modal"),f=h("jhi-item-count"),b=h("b-pagination");return u(),d("div",null,[t("h2",null,[t("span",{id:"user-management-page-heading",textContent:s(e.t$("userManagement.home.title")),"data-cy":"userManagementPageHeading"},null,8,K),t("div",R,[t("button",{class:"btn btn-info mr-2",onClick:o[0]||(o[0]=(...n)=>e.handleSyncList&&e.handleSyncList(...n)),disabled:e.isLoading},[r(m,{icon:"sync",spin:e.isLoading},null,8,["spin"]),t("span",{textContent:s(e.t$("userManagement.home.refreshListLabel"))},null,8,J)],8,q),r(p,{custom:"",to:{name:"JhiUserCreate"}},{default:c(({navigate:n})=>[t("button",{onClick:n,class:"btn btn-primary jh-create-entity"},[r(m,{icon:"plus"}),o[12]||(o[12]=S()),t("span",{textContent:s(e.t$("userManagement.home.createLabel"))},null,8,F)],8,N)]),_:1})])]),e.users?(u(),d("div",H,[t("table",T,[t("thead",null,[t("tr",null,[t("th",{scope:"col",onClick:o[1]||(o[1]=n=>e.changeOrder("id"))},[t("span",{textContent:s(e.t$("global.field.id"))},null,8,G),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"id"},null,8,["current-order","reverse"])]),t("th",{scope:"col",onClick:o[2]||(o[2]=n=>e.changeOrder("login"))},[t("span",{textContent:s(e.t$("userManagement.login"))},null,8,Q),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"login"},null,8,["current-order","reverse"])]),t("th",{scope:"col",onClick:o[3]||(o[3]=n=>e.changeOrder("email"))},[t("span",{textContent:s(e.t$("userManagement.email"))},null,8,W),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"email"},null,8,["current-order","reverse"])]),o[13]||(o[13]=t("th",{scope:"col"},null,-1)),t("th",{scope:"col",onClick:o[4]||(o[4]=n=>e.changeOrder("langKey"))},[t("span",{textContent:s(e.t$("userManagement.langKey"))},null,8,X),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"langKey"},null,8,["current-order","reverse"])]),t("th",Y,[t("span",{textContent:s(e.t$("userManagement.profiles"))},null,8,Z)]),t("th",{scope:"col",onClick:o[5]||(o[5]=n=>e.changeOrder("createdDate"))},[t("span",{textContent:s(e.t$("userManagement.createdDate"))},null,8,x),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"createdDate"},null,8,["current-order","reverse"])]),t("th",{scope:"col",onClick:o[6]||(o[6]=n=>e.changeOrder("lastModifiedBy"))},[t("span",{textContent:s(e.t$("userManagement.lastModifiedBy"))},null,8,ee),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"lastModifiedBy"},null,8,["current-order","reverse"])]),t("th",{scope:"col",id:"modified-date-sort",onClick:o[7]||(o[7]=n=>e.changeOrder("lastModifiedDate"))},[t("span",{textContent:s(e.t$("userManagement.lastModifiedDate"))},null,8,te),r(l,{"current-order":e.propOrder,reverse:e.reverse,"field-name":"lastModifiedDate"},null,8,["current-order","reverse"])]),o[14]||(o[14]=t("th",{scope:"col"},null,-1))])]),e.users?(u(),d("tbody",ne,[(u(!0),d(D,null,U(e.users,n=>(u(),d("tr",{key:n.id,id:n.login},[t("td",null,[r(p,{to:{name:"JhiUserView",params:{userId:n.login}}},{default:c(()=>[S(s(n.id),1)]),_:2},1032,["to"])]),t("td",null,s(n.login),1),t("td",se,s(n.email),1),t("td",null,[n.activated?g("",!0):(u(),d("button",{key:0,class:"btn btn-danger btn-sm deactivated",onClick:i=>e.setActive(n,!0),textContent:s(e.t$("userManagement.deactivated"))},null,8,re)),n.activated?(u(),d("button",{key:1,class:"btn btn-success btn-sm",onClick:i=>e.setActive(n,!1),disabled:e.username===n.login,textContent:s(e.t$("userManagement.activated"))},null,8,ie)):g("",!0)]),t("td",null,s(n.langKey),1),t("td",null,[(u(!0),d(D,null,U(n.authorities,i=>(u(),d("div",{key:i},[t("span",ae,s(i),1)]))),128))]),t("td",null,s(e.formatDate(n.createdDate)),1),t("td",null,s(n.lastModifiedBy),1),t("td",null,s(e.formatDate(n.lastModifiedDate)),1),t("td",null,s(n.avatar),1),t("td",le,[t("div",de,[r(p,{to:{name:"ProfileView",params:{userId:n.login}},custom:""},{default:c(({navigate:i})=>[t("button",{onClick:i,class:"btn btn-info btn-sm details"},[r(m,{icon:"eye"}),t("span",{class:"d-none d-md-inline",textContent:s(e.t$("userManagement.goToProfile"))},null,8,me)],8,ue)]),_:2},1032,["to"]),r(p,{to:{name:"JhiUserView",params:{userId:n.login}},custom:""},{default:c(({navigate:i})=>[t("button",{onClick:i,class:"btn btn-info btn-sm details"},[r(m,{icon:"eye"}),t("span",{class:"d-none d-md-inline",textContent:s(e.t$("entity.action.view"))},null,8,pe)],8,ce)]),_:2},1032,["to"]),r(p,{to:{name:"JhiUserEdit",params:{userId:n.login}},custom:""},{default:c(({navigate:i})=>[t("button",{onClick:i,class:"btn btn-primary btn-sm edit"},[r(m,{icon:"pencil-alt"}),t("span",{class:"d-none d-md-inline",textContent:s(e.t$("entity.action.edit"))},null,8,ge)],8,he)]),_:2},1032,["to"]),r(v,{onClick:i=>e.prepareRemove(n),variant:"danger",class:"btn btn-sm delete",disabled:e.username===n.login},{default:c(()=>[r(m,{icon:"times"}),t("span",{class:"d-none d-md-inline",textContent:s(e.t$("entity.action.delete"))},null,8,ve)]),_:2},1032,["onClick","disabled"])])])],8,oe))),128))])):g("",!0)]),r(C,{ref:"removeUser",id:"removeUser",title:e.t$("entity.delete.title"),onOk:o[10]||(o[10]=n=>e.deleteUser())},{"modal-footer":c(()=>[t("div",null,[t("button",{type:"button",class:"btn btn-secondary",textContent:s(e.t$("entity.action.cancel")),onClick:o[8]||(o[8]=n=>e.closeDialog())},null,8,be),t("button",{type:"button",class:"btn btn-primary",id:"confirm-delete-user",textContent:s(e.t$("entity.action.delete")),onClick:o[9]||(o[9]=n=>e.deleteUser())},null,8,$e)])]),default:c(()=>[t("div",Ce,[t("p",{id:"jhi-delete-user-heading",textContent:s(e.t$("userManagement.delete.question",{login:e.removeId}))},null,8,fe)])]),_:1},8,["title"])])):g("",!0),A(t("div",null,[t("div",ye,[r(f,{page:e.page,total:e.queryCount,"items-per-page":e.itemsPerPage},null,8,["page","total","items-per-page"])]),t("div",ke,[r(b,{size:"md","total-rows":e.totalItems,modelValue:e.page,"onUpdate:modelValue":o[11]||(o[11]=n=>e.page=n),"per-page":e.itemsPerPage,change:e.loadPage(e.page)},null,8,["total-rows","modelValue","per-page","change"])])],512),[[V,e.users&&e.users.length>0]])])}const we=j(E,[["render",Me]]);export{we as default};