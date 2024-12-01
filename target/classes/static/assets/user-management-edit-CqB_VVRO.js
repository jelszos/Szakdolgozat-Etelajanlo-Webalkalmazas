import{d as x,B as z,C as D,i as b,r as p,N as E,u as T,f as q,_ as K,j as F,t as a,s as t,I,y as o,K as r,L as u,M as v,D as i,Q as H,O as N,F as k,v as S,q as M,E as L,k as l}from"./app-9yHSs_0k.js";import{u as j}from"./index-CQAvtwPr.js";import{r as U,a as g,e as B,m as O}from"./index-DDs_-EvW.js";import{U as Z}from"./user-management.service-Bd6QAZvG.js";import{U as R}from"./user.model-DMQ3a_AX.js";import"./sorts-x2GDQqJR.js";const J=e=>e?/^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/.test(e):!0,Q={userAccount:{login:{required:U,maxLength:g(254),pattern:J},firstName:{maxLength:g(50)},lastName:{maxLength:g(50)},email:{required:U,email:B,minLength:O(5),maxLength:g(50)}}},G=x({compatConfig:{MODE:3},name:"JhiUserManagementEdit",validations:Q,setup(){var C;const e=z(),n=D(),f=b("alertService",()=>q(),!0),d=b("userManagementService",()=>new Z,!0),A=()=>n.go(-1),h=p({...new R,authorities:[]}),m=p(!1),s=p([]),c=async()=>{const $=await d.retrieveAuthorities();s.value=$.data},V=async $=>{const w=await d.get($);h.value=w.data};c();const y=(C=e.params)==null?void 0:C.userId;return y&&V(y),{alertService:f,userAccount:h,isSaving:m,authorities:s,userManagementService:d,previousState:A,v$:j(),languages:E(),t$:T().t}},methods:{save(){this.isSaving=!0,this.userAccount.id?this.userManagementService.update(this.userAccount).then(e=>{this.returnToList(),this.alertService.showInfo(this.getToastMessageFromHeader(e))}).catch(e=>{this.isSaving=!0,this.alertService.showHttpError(e.response)}):this.userManagementService.create(this.userAccount).then(e=>{this.returnToList(),this.alertService.showSuccess(this.getToastMessageFromHeader(e))}).catch(e=>{this.isSaving=!0,this.alertService.showHttpError(e.response)})},returnToList(){this.isSaving=!1,this.previousState()},getToastMessageFromHeader(e){return this.t$(e.headers["x-szakdolgozatapp-alert"],{param:decodeURIComponent(e.headers["x-szakdolgozatapp-params"].replace(/\+/g," "))}).toString()}}}),P={class:"row justify-content-center"},W={class:"col-8"},X=["textContent"],Y=["hidden"],_=["textContent"],ee={class:"form-group"},te=["textContent"],ne={key:0},se=["textContent"],oe=["textContent"],ae=["textContent"],le={class:"form-group"},ie=["textContent"],re=["placeholder"],ue={key:0},de=["textContent"],me={class:"form-group"},ce=["textContent"],ve=["placeholder"],ge={key:0},he=["textContent"],$e={class:"form-group"},pe=["textContent"],fe=["placeholder"],Ae={key:0},ye=["textContent"],Ce=["textContent"],be=["textContent"],Ne=["textContent"],ke={class:"form-check"},Se={class:"form-check-label",for:"activated"},Me=["disabled"],Le=["textContent"],Ue={key:0,class:"form-group"},Ve=["textContent"],we=["value"],xe={class:"form-group"},ze=["textContent"],De=["value"],Ee=["textContent"],Te=["disabled"],qe=["textContent"];function Ke(e,n,f,d,A,h){const m=F("font-awesome-icon");return l(),a("div",P,[t("div",W,[e.userAccount?(l(),a("form",{key:0,name:"editForm",novalidate:"",onSubmit:n[9]||(n[9]=I(s=>e.save(),["prevent"]))},[t("h2",{id:"myUserLabel",textContent:o(e.t$("userManagement.home.createOrEditLabel"))},null,8,X),t("div",null,[t("div",{class:"form-group",hidden:!e.userAccount.id},[t("label",{textContent:o(e.t$("global.field.id"))},null,8,_),r(t("input",{type:"text",class:"form-control",name:"id","onUpdate:modelValue":n[0]||(n[0]=s=>e.userAccount.id=s),readonly:""},null,512),[[u,e.userAccount.id]])],8,Y),t("div",ee,[t("label",{class:"form-control-label",textContent:o(e.t$("userManagement.login"))},null,8,te),r(t("input",{type:"text",class:v(["form-control",{valid:!e.v$.userAccount.login.$invalid,invalid:e.v$.userAccount.login.$invalid}]),name:"login","onUpdate:modelValue":n[1]||(n[1]=s=>e.v$.userAccount.login.$model=s)},null,2),[[u,e.v$.userAccount.login.$model]]),e.v$.userAccount.login.$anyDirty&&e.v$.userAccount.login.$invalid?(l(),a("div",ne,[e.v$.userAccount.login.required?i("",!0):(l(),a("small",{key:0,class:"form-text text-danger",textContent:o(e.t$("entity.validation.required"))},null,8,se)),e.v$.userAccount.login.maxLength?i("",!0):(l(),a("small",{key:1,class:"form-text text-danger",textContent:o(e.t$("entity.validation.maxlength",{max:50}))},null,8,oe)),e.v$.userAccount.login.pattern?i("",!0):(l(),a("small",{key:2,class:"form-text text-danger",textContent:o(e.t$("entity.validation.patternLogin"))},null,8,ae))])):i("",!0)]),t("div",le,[t("label",{class:"form-control-label",for:"firstName",textContent:o(e.t$("userManagement.firstName"))},null,8,ie),r(t("input",{type:"text",class:v(["form-control",{valid:!e.v$.userAccount.firstName.$invalid,invalid:e.v$.userAccount.firstName.$invalid}]),id:"firstName",name:"firstName",placeholder:e.t$("settings.form['firstname.placeholder']"),"onUpdate:modelValue":n[2]||(n[2]=s=>e.v$.userAccount.firstName.$model=s)},null,10,re),[[u,e.v$.userAccount.firstName.$model]]),e.v$.userAccount.firstName.$anyDirty&&e.v$.userAccount.firstName.$invalid?(l(),a("div",ue,[e.v$.userAccount.firstName.maxLength?i("",!0):(l(),a("small",{key:0,class:"form-text text-danger",textContent:o(e.t$("entity.validation.maxlength",{max:50}))},null,8,de))])):i("",!0)]),t("div",me,[t("label",{class:"form-control-label",for:"lastName",textContent:o(e.t$("userManagement.lastName"))},null,8,ce),r(t("input",{type:"text",class:v(["form-control",{valid:!e.v$.userAccount.lastName.$invalid,invalid:e.v$.userAccount.lastName.$invalid}]),id:"lastName",name:"lastName",placeholder:e.t$("settings.form['lastname.placeholder']"),"onUpdate:modelValue":n[3]||(n[3]=s=>e.v$.userAccount.lastName.$model=s)},null,10,ve),[[u,e.v$.userAccount.lastName.$model]]),e.v$.userAccount.lastName.$anyDirty&&e.v$.userAccount.lastName.$invalid?(l(),a("div",ge,[e.v$.userAccount.lastName.maxLength?i("",!0):(l(),a("small",{key:0,class:"form-text text-danger",textContent:o(e.t$("entity.validation.maxlength",{max:50}))},null,8,he))])):i("",!0)]),t("div",$e,[t("label",{class:"form-control-label",for:"email",textContent:o(e.t$("userManagement.email"))},null,8,pe),r(t("input",{type:"email",class:v(["form-control",{valid:!e.v$.userAccount.email.$invalid,invalid:e.v$.userAccount.email.$invalid}]),id:"email",name:"email",placeholder:e.t$("global.form['email.placeholder']"),"onUpdate:modelValue":n[4]||(n[4]=s=>e.v$.userAccount.email.$model=s),email:"",required:""},null,10,fe),[[u,e.v$.userAccount.email.$model]]),e.v$.userAccount.email.$anyDirty&&e.v$.userAccount.email.$invalid?(l(),a("div",Ae,[e.v$.userAccount.email.required?i("",!0):(l(),a("small",{key:0,class:"form-text text-danger",textContent:o(e.t$("global.messages.validate.email.required"))},null,8,ye)),e.v$.userAccount.email.email?i("",!0):(l(),a("small",{key:1,class:"form-text text-danger",textContent:o(e.t$("global.messages.validate.email.invalid"))},null,8,Ce)),e.v$.userAccount.email.minLength?i("",!0):(l(),a("small",{key:2,class:"form-text text-danger",textContent:o(e.t$("global.messages.validate.email.minlength"))},null,8,be)),e.v$.userAccount.email.maxLength?i("",!0):(l(),a("small",{key:3,class:"form-text text-danger",textContent:o(e.t$("global.messages.validate.email.maxlength"))},null,8,Ne))])):i("",!0)]),t("div",ke,[t("label",Se,[r(t("input",{class:"form-check-input",disabled:e.userAccount.id===null,type:"checkbox",id:"activated",name:"activated","onUpdate:modelValue":n[5]||(n[5]=s=>e.userAccount.activated=s)},null,8,Me),[[H,e.userAccount.activated]]),t("span",{textContent:o(e.t$("userManagement.activated"))},null,8,Le)])]),e.languages&&Object.keys(e.languages).length>0?(l(),a("div",Ue,[t("label",{for:"langKey",textContent:o(e.t$("userManagement.langKey"))},null,8,Ve),r(t("select",{class:"form-control",id:"langKey",name:"langKey","onUpdate:modelValue":n[6]||(n[6]=s=>e.userAccount.langKey=s)},[(l(!0),a(k,null,S(e.languages,(s,c)=>(l(),a("option",{value:c,key:c},o(s.name),9,we))),128))],512),[[N,e.userAccount.langKey]])])):i("",!0),t("div",xe,[t("label",{textContent:o(e.t$("userManagement.profiles"))},null,8,ze),r(t("select",{class:"form-control",multiple:"",name:"authority","onUpdate:modelValue":n[7]||(n[7]=s=>e.userAccount.authorities=s)},[(l(!0),a(k,null,S(e.authorities,s=>(l(),a("option",{value:s,key:s},o(s),9,De))),128))],512),[[N,e.userAccount.authorities]])])]),t("div",null,[t("button",{type:"button",class:"btn btn-secondary",onClick:n[8]||(n[8]=s=>e.previousState())},[M(m,{icon:"ban"}),n[10]||(n[10]=L(" ")),t("span",{textContent:o(e.t$("entity.action.cancel"))},null,8,Ee)]),t("button",{type:"submit",disabled:e.v$.userAccount.$invalid||e.isSaving,class:"btn btn-primary"},[M(m,{icon:"save"}),n[11]||(n[11]=L(" ")),t("span",{textContent:o(e.t$("entity.action.save"))},null,8,qe)],8,Te)])],32)):i("",!0)])])}const Ze=K(G,[["render",Ke]]);export{Ze as default};
