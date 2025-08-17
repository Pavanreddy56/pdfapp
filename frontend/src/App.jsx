import React from 'react'
import { Outlet, useNavigate } from 'react-router-dom'
export default function App(){
  const nav = useNavigate()
  return (<div>
    <header style={{display:'flex',justifyContent:'space-between',padding:'10px',borderBottom:'1px solid #eee'}}>
      <b>Secure PDF Portal</b>
      <button onClick={()=>{localStorage.removeItem('token'); nav('/login')}}>Logout</button>
    </header>
    <main style={{padding:'16px'}}><Outlet/></main>
  </div>)
}
