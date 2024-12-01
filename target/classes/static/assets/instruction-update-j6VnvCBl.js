import{d as q,i as c,r as h,B as I,C as V,u as A,f as E,c as R,_ as U,j as B,t as p,s as i,I as L,y as o,K as l,L as v,D as M,M as g,O as D,F as H,v as N,q as C,E as b,k as m}from"./app-DpqgLFQi.js";import{u as j}from"./index-BKG5m1Gr.js";import{I as F}from"./instruction.service-eQi2CHjJ.js";import{R as K}from"./recipe.service-Bi-8z6n7.js";class O{constructor(e,s,d,a,u,r){this.id=e,this.title=s,this.requiredTime=d,this.description=a,this.ingredients=u,this.recipe=r}}const G=q({compatConfig:{MODE:3},name:"InstructionUpdate",setup(){var y;const t=c("instructionService",()=>new F),e=c("alertService",()=>E(),!0),s=h(new O),d=c("recipeService",()=>new K),a=h([]),u=h(!1),r=c("currentLanguage",()=>R(()=>navigator.language??"en"),!0),n=I(),z=V(),k=()=>z.go(-1),w=async f=>{try{const $=await t().find(f);s.value=$}catch($){e.showHttpError($.response)}};(y=n.params)!=null&&y.instructionId&&w(n.params.instructionId),(()=>{d().retrieve().then(f=>{a.value=f.data})})();const{t:T}=A(),S=j({title:{},requiredTime:{},description:{},recipe:{}},s);return S.value.$validate(),{instructionService:t,alertService:e,instruction:s,previousState:k,isSaving:u,currentLanguage:r,recipes:a,v$:S,t$:T}},created(){},methods:{save(){this.isSaving=!0,this.instruction.id?this.instructionService().update(this.instruction).then(t=>{this.isSaving=!1,this.previousState(),this.alertService.showInfo(this.t$("szakdolgozatApp.instruction.updated",{param:t.id}))}).catch(t=>{this.isSaving=!1,this.alertService.showHttpError(t.response)}):this.instructionService().create(this.instruction).then(t=>{this.isSaving=!1,this.previousState(),this.alertService.showSuccess(this.t$("szakdolgozatApp.instruction.created",{param:t.id}).toString())}).catch(t=>{this.isSaving=!1,this.alertService.showHttpError(t.response)})}}}),J={class:"row justify-content-center"},P={class:"col-8"},Q=["textContent"],W={key:0,class:"form-group"},X=["textContent"],Y={class:"form-group"},Z=["textContent"],_={class:"form-group"},x=["textContent"],tt={class:"form-group"},et=["textContent"],it={class:"form-group"},nt=["textContent"],ot=["value"],st=["textContent"],rt=["disabled"],at=["textContent"];function lt(t,e,s,d,a,u){const r=B("font-awesome-icon");return m(),p("div",J,[i("div",P,[i("form",{name:"editForm",novalidate:"",onSubmit:e[6]||(e[6]=L(n=>t.save(),["prevent"]))},[i("h2",{id:"szakdolgozatApp.instruction.home.createOrEditLabel","data-cy":"InstructionCreateUpdateHeading",textContent:o(t.t$("szakdolgozatApp.instruction.home.createOrEditLabel"))},null,8,Q),i("div",null,[t.instruction.id?(m(),p("div",W,[i("label",{for:"id",textContent:o(t.t$("global.field.id"))},null,8,X),l(i("input",{type:"text",class:"form-control",id:"id",name:"id","onUpdate:modelValue":e[0]||(e[0]=n=>t.instruction.id=n),readonly:""},null,512),[[v,t.instruction.id]])])):M("",!0),i("div",Y,[i("label",{class:"form-control-label",textContent:o(t.t$("szakdolgozatApp.instruction.title")),for:"instruction-title"},null,8,Z),l(i("input",{type:"text",class:g(["form-control",{valid:!t.v$.title.$invalid,invalid:t.v$.title.$invalid}]),name:"title",id:"instruction-title","data-cy":"title","onUpdate:modelValue":e[1]||(e[1]=n=>t.v$.title.$model=n)},null,2),[[v,t.v$.title.$model]])]),i("div",_,[i("label",{class:"form-control-label",textContent:o(t.t$("szakdolgozatApp.instruction.requiredTime")),for:"instruction-requiredTime"},null,8,x),l(i("input",{type:"number",class:g(["form-control",{valid:!t.v$.requiredTime.$invalid,invalid:t.v$.requiredTime.$invalid}]),name:"requiredTime",id:"instruction-requiredTime","data-cy":"requiredTime","onUpdate:modelValue":e[2]||(e[2]=n=>t.v$.requiredTime.$model=n)},null,2),[[v,t.v$.requiredTime.$model,void 0,{number:!0}]])]),i("div",tt,[i("label",{class:"form-control-label",textContent:o(t.t$("szakdolgozatApp.instruction.description")),for:"instruction-description"},null,8,et),l(i("input",{type:"text",class:g(["form-control",{valid:!t.v$.description.$invalid,invalid:t.v$.description.$invalid}]),name:"description",id:"instruction-description","data-cy":"description","onUpdate:modelValue":e[3]||(e[3]=n=>t.v$.description.$model=n)},null,2),[[v,t.v$.description.$model]])]),i("div",it,[i("label",{class:"form-control-label",textContent:o(t.t$("szakdolgozatApp.instruction.recipe")),for:"instruction-recipe"},null,8,nt),l(i("select",{class:"form-control",id:"instruction-recipe","data-cy":"recipe",name:"recipe","onUpdate:modelValue":e[4]||(e[4]=n=>t.instruction.recipe=n)},[e[7]||(e[7]=i("option",{value:null},null,-1)),(m(!0),p(H,null,N(t.recipes,n=>(m(),p("option",{value:t.instruction.recipe&&n.id===t.instruction.recipe.id?t.instruction.recipe:n,key:n.id},o(n.id),9,ot))),128))],512),[[D,t.instruction.recipe]])])]),i("div",null,[i("button",{type:"button",id:"cancel-save","data-cy":"entityCreateCancelButton",class:"btn btn-secondary",onClick:e[5]||(e[5]=n=>t.previousState())},[C(r,{icon:"ban"}),e[8]||(e[8]=b(" ")),i("span",{textContent:o(t.t$("entity.action.cancel"))},null,8,st)]),i("button",{type:"submit",id:"save-entity","data-cy":"entityCreateSaveButton",disabled:t.v$.$invalid||t.isSaving,class:"btn btn-primary"},[C(r,{icon:"save"}),e[9]||(e[9]=b(" ")),i("span",{textContent:o(t.t$("entity.action.save"))},null,8,at)],8,rt)])],32)])])}const ft=U(G,[["render",lt]]);export{ft as default};