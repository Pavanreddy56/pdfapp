import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api, setToken } from '../api'
export default function Login(){
  const [username,setUsername]=useState('')
  const [password,setPassword]=useState('')
  const [err,setErr]=useState('')
  const nav = useNavigate()
  async function onSubmit(e){
    e.preventDefault()
    try{
      const { data } = await api.post('/auth/login', { username, password })
      localStorage.setItem('token', data.token); setToken(data.token); nav('/dashboard')
    }catch(ex){ setErr('Invalid credentials') }
  }
  return (<div style={{maxWidth:380,margin:'40px auto',border:'1px solid #eee',padding:16,borderRadius:12}}>
    <h3>Login</h3>
    {err && <p style={{color:'red'}}>{err}</p>}
    <form onSubmit={onSubmit}>
      <input placeholder="Username" value={username} onChange={e=>setUsername(e.target.value)} style={{width:'100%',padding:8,margin:'6px 0'}}/>
      <input type="password" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} style={{width:'100%',padding:8,margin:'6px 0'}}/>
      <button style={{width:'100%',padding:10}}>Sign in</button>
    </form>
    <p>No account? <Link to="/register">Register</Link></p>
  </div>)
}
