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
        const [userList, setUserList] = useState([]);

        useEffect(() => {
            (async () => {
                const res = await fetch("api/user");
                setUserList(await res.json());
                setLoading(false);
            })()
        }, [])

        if (loading) {
            return <div>Loading users...</div>
        }

        return <div className={"user_grid"} style={{fontWeight: "bold"}}>
                <h1>Users</h1>
                <ul>
                    {userList.map(user => <button onClick={() => {SelectUser(user); SelectGroup(null)}} className={"single_user"}>{user.username}</button>)}
                </ul>
        </div>
    }

    function ListCurrentGroups() {
        const [loading, setLoading] = useState(true);
        const [groupList, setGroupList] = useState([]);

        useEffect(() => {
            (async () => {
                const res = await fetch("api/group/" + currentUser.id);
                setGroupList(await res.json());
                setLoading(false);
            })()
        }, [])

        if (loading) {
            return <div>Loading groups...</div>
        }

        return <div className={"user_group"} style={{fontWeight: "-moz-initial"}}>
            <h1>Message Groups</h1>
            <ul>
                {groupList.map(group => <button onClick={() => SelectGroup(group)} className={"single_group"} >{group.groupName}</button> )}
            </ul>
        </div>
    }


    function ListGroupMessages() {
        const [loading, setLoading] = useState(true);
        const [messageList, setMessageList] = useState([]);
        const [groupUserList, setGroupUserList] = useState([]);

        useEffect(() => {
            (async () => {
                const res = await fetch("api/message/" + currentGroup.id);
                setMessageList(await res.json());
                setLoading(false);
            })()
        }, [])

        useEffect(() => {
            (async () => {
                const res = await fetch("api/group/" + currentGroup.id + "/users");
                setGroupUserList(await res.json());
            })()
        }, [])

        if (loading) {
            return <div>Loading messages...</div>
        }
        return <div className={"group_message"} style={{fontWeight: "-moz-initial"}}>
            <div>Members: {groupUserList.map(user => <div>{user.username}</div>)}</div>
            <h1>Messages in this Group</h1>
            <ul>
                {messageList.map(message =><div className={"message_div"}>
                    <div className={"message_sender"}> {
                        // this "" is super funky, because if you don't have it the site crashes randomly when selecting a group
                        // with the error "g.find is undefined".
                        (groupUserList.find(user => user.id === message.senderId) || "").username
                    }
                    </div>
                    <div className={"message_body"} >{message.message}</div>
                </div>)}
            </ul>
        </div>
    }


//groupUserList.find(user => user.id === message.senderId).username
    return (
        <div className="App">
            <ListUsers/>
            <ListCurrentGroups/>
            <ListGroupMessages/>
        </div>
    )
}

export default App
