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
    }, [])

    if (loading) {
        return <div>Loading users...</div>
    }

    return <div className={"user_grid"} style={{fontWeight: "bold"}}>
            <h1>Users</h1>
            <ul>
                {username.map(user => <div>{user.username}</div>)}
            </ul>
    </div>
}

export default App
