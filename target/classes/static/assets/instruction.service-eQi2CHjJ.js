import{J as n}from"./app-DpqgLFQi.js";const c="api/instructions";class o{find(t){return new Promise((a,r)=>{n.get(`${c}/${t}`).then(e=>{a(e.data)}).catch(e=>{r(e)})})}retrieve(){return new Promise((t,a)=>{n.get(c).then(r=>{t(r)}).catch(r=>{a(r)})})}delete(t){return new Promise((a,r)=>{n.delete(`${c}/${t}`).then(e=>{a(e)}).catch(e=>{r(e)})})}create(t){return new Promise((a,r)=>{n.post(`${c}`,t).then(e=>{a(e.data)}).catch(e=>{r(e)})})}update(t){return new Promise((a,r)=>{n.put(`${c}/${t.id}`,t).then(e=>{a(e.data)}).catch(e=>{r(e)})})}partialUpdate(t){return new Promise((a,r)=>{n.patch(`${c}/${t.id}`,t).then(e=>{a(e.data)}).catch(e=>{r(e)})})}}export{o as I};