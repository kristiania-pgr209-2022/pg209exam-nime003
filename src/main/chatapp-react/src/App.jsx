import {useEffect, useState} from 'react'
import './App.css'

function App() {

    const [currentUser, setCurrentUser] = useState([]);
    const [currentGroup, setCurrentGroup] = useState([]);

    function SelectUser(user) {
        setCurrentUser(user);
    }

    function SelectGroup(group) {
        setCurrentGroup(group);
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
                    {username.map(user => <button onClick={() => SelectUser(user)} className={"single_user"} >{user.username}</button>)}
                </ul>
        </div>
    }

    function ListCurrentGroups() {
        return <div className={"user_group"} style={{fontWeight: "-moz-initial"}}>
            <h1>Message Groups</h1>
            <ul>
                {group.map(group => <button onClick={() => SelectGroup(group)} className={"single_group"} >{currentUser.group}</button> )}
            </ul>
        </div>
    }



    return (
        <div className="App">
            <ListUsers/>
            <ListCurrentGroups/>
        </div>
    )
}

export default App
