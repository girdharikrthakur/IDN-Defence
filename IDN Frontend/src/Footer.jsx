// src/components/Footer.jsx
const Footer=()=> {
  return (
    <footer className="bg-gray-900 text-gray-300 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Top Section */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Brand / About */}
          <div>
            <h2 className="text-xl font-bold text-white">MyWebsite</h2>
            <p className="mt-3 text-sm">
              Building modern web applications with passion and creativity.
            </p>
          </div>

          {/* Navigation */}
          <div>
            <h3 className="text-lg font-semibold text-white mb-3">Quick Links</h3>
            <ul className="space-y-2 text-sm">
              <li><a href="#home" className="hover:text-red-400">Home</a></li>
              <li><a href="#about" className="hover:text-red-400">About</a></li>
              <li><a href="#services" className="hover:text-red-400">Services</a></li>
              <li><a href="#contact" className="hover:text-red-400">Contact</a></li>
            </ul>
          </div>

          {/* Socials */}
          <div>
            <h3 className="text-lg font-semibold text-white mb-3">Follow Us</h3>
            <div className="flex space-x-4">
              <a href="#" className="hover:text-red-400">🐦 Twitter</a>
              <a href="#" className="hover:text-red-400">📘 Facebook</a>
              <a href="#" className="hover:text-red-400">📸 Instagram</a>
              <a href="#" className="hover:text-red-400">💼 LinkedIn</a>
            </div>
          </div>
        </div>

        {/* Divider */}
        <div className="border-t border-gray-700 mt-8 pt-6 text-center text-sm">
          © {new Date().getFullYear()} MyWebsite. All rights reserved.
        </div>
      </div>
    </footer>
  );
}


export default Footer