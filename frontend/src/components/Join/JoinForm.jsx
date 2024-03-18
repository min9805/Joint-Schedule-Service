import React from 'react'

const JoinForm = ({ join }) => {

    const onJoin = (e) => {
        e.preventDefault()      // submit 기본 동작 방지
        const form = e.target
        const loginId = form.loginId.value
        const password = form.password.value
        const name = form.name.value
        const email = form.email.value
        const birthDate = form.birthDate.value
        const gender = form.gender.value

        console.log(loginId, password, name, email);

        join({ loginId: loginId, password: password, name, email, birthDate, gender })
    }

    return (
        <div className="form">
            <h2 className="login-title">Join</h2>

            <form className='login-form' onSubmit={(e) => onJoin(e)}>
                <div>
                    <label htmlFor="name">Login ID</label>
                    <input type="text"
                        id='loginId'
                        placeholder='loginId'
                        name='loginId'
                        autoComplete='loginId'
                        required
                    />
                </div>

                <div>
                    <label htmlFor="password">password</label>
                    <input type="password"
                        id='passowrd'
                        placeholder='password'
                        name='password'
                        autoComplete='password'
                        required
                    />
                </div>

                <div>
                    <label htmlFor="name">Name</label>
                    <input type="text"
                        id='name'
                        placeholder='name'
                        name='name'
                        autoComplete='name'
                        required
                    />
                </div>

                <div>
                    <label htmlFor="name">Email</label>
                    <input type="text"
                        id='email'
                        placeholder='email'
                        name='email'
                        autoComplete='email'
                        required
                    />
                </div>

                <div>
                    <label htmlFor="birthDate">Birth Date</label>
                    <input type="text"
                        id='birthDate'
                        placeholder='YYYY-MM-dd'
                        name='birthDate'
                        autoComplete='bday'
                        pattern="\d{4}-\d{2}-\d{2}"
                        required
                    />
                </div>

                <div>
                    <label htmlFor="gender">Gender</label>
                    <input type="text"
                        id='gender'
                        placeholder='MAN or WOMAN'
                        name='gender'
                        autoComplete='gender'
                        required
                    />
                </div>



                <button type='submit' className='btn btn--form btn-login'>
                    Join
                </button>
            </form>
        </div>
    )
}

export default JoinForm