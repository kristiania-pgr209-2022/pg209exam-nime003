import {useEffect, useReducer, useState} from 'react'
import './App.css'

function App() {

    const [currentUser, setCurrentUser] = useState([]);
    const [currentGroup, setCurrentGroup] = useState([]);
    const [updateUsers, forceUpdateUsers] = useReducer(x => x + 1, 0);
    const [updateMessages, forceUpdateMessages] = useReducer(x => x + 1, 0);

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
        }, [updateUsers])

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
            return <div>Select a user to see the groups they are in</div>
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
                const res1 = await fetch("api/message/" + currentGroup.id);
                setMessageList(await res1.json());
                setLoading(false);
                const res2 = await fetch("api/group/" + currentGroup.id + "/users");
                setGroupUserList(await res2.json());
            })()
        }, [updateMessages])

        if (loading) {
            return <div>Select a group to see messages</div>
        }
        return <div className={"group_div"} style={{fontWeight: "-moz-initial"}}>
            <div>Members: {groupUserList.map(user => <div>{user.username}</div>)}</div>
            <h1>Messages in this Group</h1>
            <div className={"message_div"}>
                <SendMessage/>
                <ul className={"message_list"}>
                    {messageList.map(message => <div className={"single_message"}>
                        <div className={"message_sender"}> {
                            // this "" is super funky, because if you don't have it the site crashes randomly when selecting a group
                            // with the error "g.find is undefined".
                            (groupUserList.find(user => user.id === message.userId) || "").username
                        }
                        </div>
                        <div className={"message_body"} >{message.message}</div>
                        <div className={"message_timestamp"}>{message.dateTimeSent}</div>
                    </div>)}
                </ul>
            </div>
        </div>
    }

    function CreateUser() {
        const [username, setUsername] = useState("");
        const [password, setPassword] = useState("");

        async function HandleSubmit(e) {
            e.preventDefault();
            if (username !== ""){
                await fetch("/api/user", {
                    method: "post",
                    body: JSON.stringify({username, password}),
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(forceUpdateUsers);
            }
        }

        return <div>
            <form onSubmit={HandleSubmit}>
                <div>Username: <input type={"text"} value={username} onChange={event => {setUsername(event.target.value)}}/></div>
                <div>Password: <input type={"text"} value={password} onChange={event => {setPassword(event.target.value)}}/></div>
                <button>Create User</button>
            </form>
        </div>
    }

    function SendMessage(){
        const [messageText, setMessageText] = useState("");

        async function HandleSubmit(e) {

            e.preventDefault();
            if (messageText !== "") {
                await fetch("/api/message", {
                    method: "post",
                    body: JSON.stringify({userId : currentUser.id, groupId : currentGroup.id, message : messageText}),
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(forceUpdateMessages);
            }
        }

        return <div>
            <form onSubmit={HandleSubmit}>
                <div><input type={"text"} value={messageText} onChange={event => {setMessageText(event.target.value)}}/></div>
                <button>Send message</button>
            </form>
        </div>;
    }


    return (
        <div className="App">
            <CreateUser/>
            <ListUsers/>
            <ListCurrentGroups/>
            <ListGroupMessages/>
        </div>
    )
}

export default App
