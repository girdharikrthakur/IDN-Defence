const Header=()=>{


    return(

    <header className="bg-gray-800 text-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo / Brand */}
          <div className="text-2xl font-bold tracking-wide">
            India Defenece News
          </div>

          <nav className="hidden md:flex space-x-6 items-center ">
            <a href="#home" className="hover:text-gray-200">Home</a>
            <a href="#about" className="hover:text-gray-200">India</a>
            <a href="#services" className="hover:text-gray-200">World</a>
            <a href="#contact" className="hover:text-gray-200">Contact</a>

            <button className="bg-white text-gray-600 px-4 py-2 rounded-sm font-medium hover:bg-gray-100 transition">
              Sign In
            </button>
            </nav>
    
        </div>
      </div>
    </header>
    )

}

export default Header