import React from 'react'

const UserForm = ({ userInfo, updateUser, deleteUser }) => {

    const onUpdate = (e) => {
        e.preventDefault()

        const form = e.target
        const updateUserFields = {}

        // 각 폼 필드의 값이 비어있지 않은 경우에만 해당 필드를 updateUserFields 객체에 추가합니다.
        if (form.username.value.trim()) updateUserFields.loginId = form.username.value
        if (form.password.value.trim()) updateUserFields.password = form.password.value
        if (form.name.value.trim()) updateUserFields.name = form.name.value
        if (form.email.value.trim()) updateUserFields.email = form.email.value


        console.log(updateUserFields);

        updateUser( updateUserFields )
    }

    return (
        <div className="form">
            <h2 className="login-title">UserInfo</h2>

            <form className='login-form' onSubmit={ (e) => onUpdate(e) }>
                <div>
                    <label htmlFor="name">username</label>
                    <input type="text"
                            id='username'
                            placeholder='username'
                            name='username'
                            autoComplete='username'
                            required
                            readOnly
                            defaultValue={ userInfo?.loginId }
                    />
                </div>

                <div>
                    <label htmlFor="password">password</label>
                    <input type="password"
                            id='passowrd'
                            placeholder='password'
                            name='password'
                            autoComplete='password'
                    />
                </div>

                <div>
                    <label htmlFor="name">Name</label>
                    <input type="text"
                            id='name'
                            placeholder='name'
                            name='name'
                            autoComplete='name'
                            defaultValue={ userInfo?.name }
                    />
                </div>

                <div>
                    <label htmlFor="name">Email</label>
                    <input type="text"
                            id='email'
                            placeholder='email'
                            name='email'
                            autoComplete='email'
                            defaultValue={ userInfo?.email }
                    />
                </div>


                <button type='submit' className='btn btn--form btn-login'>
                    정보 수정     
                </button>
                <button type='button' className='btn btn--form btn-login'
                        onClick={ () => deleteUser(userInfo.userId)} >
                    회원 탈퇴
                </button>
            </form>
        </div>
    )
}

export default UserForm