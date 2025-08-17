import React, { useEffect, useState } from 'react'
import { api, setToken } from '../api'
export default function Dashboard(){
  const [list,setList]=useState([])
  const [file,setFile]=useState(null)
  useEffect(()=>{ const t=localStorage.getItem('token'); setToken(t); fetchList() },[])
  async function fetchList(){ const { data } = await api.get('/files'); setList(data) }
  async function onUpload(e){ e.preventDefault(); if(!file) return; const fd = new FormData(); fd.append('file', file); await api.post('/files/upload', fd, { headers:{'Content-Type':'multipart/form-data'} }); setFile(null); await fetchList() }
  function downloadUrl(id){ return `${api.defaults.baseURL}/files/${id}` }
  return (<div style={{maxWidth:700,margin:'20px auto'}}>
    <h3>Your PDFs</h3>
    <form onSubmit={onUpload} style={{display:'flex',gap:8,alignItems:'center',margin:'12px 0'}}>
      <input type="file" accept="application/pdf" onChange={e=>setFile(e.target.files?.[0])}/>
      <button>Upload</button>
    </form>
    <ul>
      {list.map(doc => (<li key={doc.id} style={{display:'flex',justifyContent:'space-between',padding:'8px 0',borderBottom:'1px solid #eee'}}>
        <span>{doc.filename} <small>#{doc.id}</small></span>
        <a href={downloadUrl(doc.id)}>Download</a>
      </li>))}
    </ul>
  </div>)
}
