function e(n){return n?Object.entries(n).map(([t,r])=>Array.isArray(r)?r.map(i=>`${t}=${i}`).join("&"):`${t}=${r}`).join("&"):""}export{e as b};
