import {useEffect, useState} from 'react'
import './App.css'

function App() {

  return (
    <div className="App">
        <ListUsers/>
    </div>
  )
}

function ListUsers() {
    const [loading, setLoading] = useState(true);
    const [username, setUsername] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await fetch("api/user");
            setUsername(await res.json());
            setLoading(false);
        })()
    })

    if (loading) {
        return <div>Loading users...</div>
    }
    return <ul>
        <div className={"usergrid"} style={{fontWeight: "bold"}}>Users</div>
        {username.map(p => <div>{username}</div>)}
    </ul>
}

export default App
